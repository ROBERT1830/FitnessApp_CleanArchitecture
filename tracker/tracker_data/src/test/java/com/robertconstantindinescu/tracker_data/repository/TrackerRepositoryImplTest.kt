package com.robertconstantindinescu.tracker_data.repository



import com.google.common.truth.Truth.assertThat
import com.plcoding.tracker_data.remote.malformedFoodResponse
import com.plcoding.tracker_data.remote.validFoodResponse
import com.robertconstantindinescu.tracker_data.remote.OpenFoodApi
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

/**
 * This clas will be ued to test an api itnegration
 * A unit test should run very quicikly, but calling an api is anything but quick o how do we solve this problem
 * Luckely there is a mock web server wchic hcomes from squere (the same as retrofit library)
 * and whith this mock server we can use it for the retrofit instance and simulate responses.
 * So we can sey ok taht mock server should now return this resposne, or tath resposne or this statud code
 *
 * And whith that we can very easyly test the data integration with taht api.
 */
class TrackerRepositoryImplTest {

    /**Create the test instance, in this case is hte repo impl*/
    private lateinit var repository: TrackerRepositoryImpl
    private lateinit var mockWebServer: MockWebServer
    private lateinit var okHttpClient: OkHttpClient
    private lateinit var api: OpenFoodApi



    @Before
    fun setUp() {


        /**Initialize the repo
         * Tehn will notice tht awe ned a dao and an api. Hw to get that
         *
         * We  could create mocks to do that but for the api a mock dont work because we cant really
         * determine if the api fails or gives correct response
         *
         * For the dao we will use a mock. This is ok in this way because, for the function we test our
         * serach food function we dont need a dao
         *
         * How to get instance form api. We could use the retrofit builder but that would now
         * will contruct the api that make the call and we wanto to avoid taht.
         *
         * So firt of all we declare a variable mockwebserver. Then we wanto to have an ok http cliente which we can sue to configure the
         * we bserver. We wantot o ahve a var for api openfoodapi.
         *
         * Before iniitalize the repo, letts init in the setUp all the variables
         * create the time responds of the okhttp
         *
         * Then init api with the okhttpclient
         *
         *As a base url we can get it from the mock web server. so the mock webserver wil now create a local
         * web server taht runs temporarely. and taht will provide base url for api.
         * And our retrifit instance wiil simple will talk to the local web server that comes from the mock webserver instane
         *
         *
         * So we contructed the retorfit isntanc as we normally do. But we say ok
         * please dont talk to the api please tock to the webserver that comes from webserver instance.
         *
         * Then use the apo an pase it to the repo
         * */

        mockWebServer = MockWebServer()
        okHttpClient = OkHttpClient.Builder()
            //this is because if theere are some issues by deffual the wait to resposne of failure is 10 seconds of wait. Here
            //we use 1 second to nos block that much time the test
            //and if there is a response htey will be coming more quicker taht 1 s
            .writeTimeout(1, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.SECONDS)
            .connectTimeout(1, TimeUnit.SECONDS)
            .build()
        api = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(mockWebServer.url("/"))
            .build()
            .create(OpenFoodApi::class.java)

        repository = TrackerRepositoryImpl(
            dao = mockk(relaxed = true),
            api = api
        )
    }

    /**
     * This will execute after every single use case
     * Because we will wantot shutdown the server. close it and we dont want to
     * have and other instance when call each time the test.
     */
    @After
    fun terDown(){
        mockWebServer.shutdown()

    }

    /**
     * Write the test case
     * what we expect is to return results
     *
     * rublocking becuase we make a api call. Will aloow to execute the serachFood function
     */
    @Test
    fun `Search food, valid response, returns results` () = runBlocking {
        //take the mokwebserver and use enqueue, to enque a mock response  which we can now
        //construct.
        /*now we can say the mock server should know respond witha a specific repsose
        * like for example 200 code which is ok status code
        * We can set the body to a specific string which is the string with which response the mockserver
        * for each testcase
        *
        * For that body we need some kind of sample repsonse that will be valid. We have it from
        * the valid api response json in remote package from test
        * */

        //specofy what the server will repsonse
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(validFoodResponse)
        )
        3
        /*Now make the api request*/
        val result = repository.searchFood("banana", 1, 40)

        /*Now take te resutl and make sure that the result is succesfull
        * In the repository iompl we responde with the Result.succes. */


        //so we just make usre that we enque the valid response in the server ant then we anser with taht
        //them make the request to taht server. And make sure that is succesfull because we have a
        //valid resposne in the server enqueue
        assertThat(result.isSuccess).isTrue()

        /**
         * Now do the same for the invalid response and not found response
         */


    }

    @Test
    fun `Search food, invalid response, returns failure` () = runBlocking {

        //specofy what the server will repsonse
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(403) //invalid code
                .setBody(validFoodResponse)
        )
        3
        /*Now make the api request*/
        val result = repository.searchFood("banana", 1, 40)
        assertThat(result.isFailure).isTrue() //now we wanto failure to be true
    }

    /**
     * Test that for the invalid JSON bod. We have InvalidFoodReposnse for that test in remote
     * package. We can do whatever in the json to brak it like write lo que te de la puta gana
     * dentro
     */
    @Test
    fun `Search food, malformed response, returns failure` () = runBlocking {

        //specofy what the server will repsonse
        mockWebServer.enqueue(
            MockResponse()
                .setBody(malformedFoodResponse)
        )
        3
        /*Now make the api request*/
        val result = repository.searchFood("banana", 1, 40)
        assertThat(result.isFailure).isTrue() //now we wanto failure to be true
    }
}






































