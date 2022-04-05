package com.robertconstantindinescu.tracker_domain.use_case

import com.google.common.truth.Truth.assertThat
import com.robertconstantindinescu.core.domain.model.ActivityLevel
import com.robertconstantindinescu.core.domain.model.Gender
import com.robertconstantindinescu.core.domain.model.GoalType
import com.robertconstantindinescu.core.domain.model.UserInfo
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import com.robertconstantindinescu.core.domain.preferences.Preferences
import com.robertconstantindinescu.tracker_domain.model.MealType
import com.robertconstantindinescu.tracker_domain.model.TrackedFood
import org.junit.Test
import java.time.LocalDate
import kotlin.random.Random


class CalculateMealNutrientsTest {

    /**
     * When you start to write a test you need the instance of hte class you want to test
     * In our case it our use case. So use lateinit
     */

    private lateinit var calculateMealNutrients: CalculateMealNutrients

    /**For test we have a set up function
     * we annotate this with @Before. This come from junit which is the testing framework we
     * use on adroid. This framework will find all the test cases in a aspecific package and
     * then tell you this test pass this not and so on.
     * With this before anotation me make sure that the code in the setup function is run before every single test case
     * in this class. That is very importatn ebcause we wanto to start with the initiall setup. We dont
     * wantot o reuse the same instance across multiple test cases. So if we actually initialize our class above
     * that would mean that we reinitialize it before every single test case. This is exactly
     * what we should do. Because of that you just abod that test be failed just because
     * test A did some to that specific instance of class. So we allways need to start
     * witha a brand new instance for every single test case
     *
     * So we will initialize the use case in the set up function
     * And we will se that we need references. So how get the rpeferecens obejct n the tes class.
     * We dont wantot o take the shared preferencees directly and use it for the test. That will be terrible,
     * will take so many time and we dont wanto to interact with shared pref. And we cant use
     * preferences here because it needs context to be initialzed. And here we are in a class that
     * ca not have acces to android dependeices. We are in a normal test package that dont have acces to
     * android dependenices like context.
     *
     * So we have different options.
     * 1- Since we use a preference interface we dont specify that this need to use shared pref. What we could do is
     * create our very own test version of the preference object.
     * So maybe could be a object that could uses normal list taht stores the preferences. And just simulate
     * the shred pref. That is called fake. write something for the use cases that simulates the normal behaviour
     *
     * 2--> Create a mock. Is basically a version of a specific depednency where we can decide what it
     * will return in specifiic casses.
     *
     * So we create the pref object. And since we use our mockk library that is already included in the dependencies.
     * Inside it we specify the obect we wanto to mock which is a preferences object from core domain
     * and se relaxed to true. --> That will just give all of our function inside our preference object some empty reponses.
     * And if we take a look in calcuateMealNutrients the only thing our use case need from our preferences is
     * loadUserinfo fucniotn.
     *
     * So what we can do with the mock is we can now use it to determine that this mock should return
     * some kind of specific user info for every time this function is called. So for each time we call
     * loadUserinfo please return the mock object.
     *
     * We can do this by using every. So every preferences.loadUserinfo return a specific
     * user info object whcih now we can provide. And now make the return type you self.
     * */


    @Before
    fun setUp() {
        val preferences =
            mockk<Preferences>(relaxed = true) //relaxed the crea el objeto con totdas las fucnione spero con un comportamiento vacio, es decirno hacen nada
        every { preferences.loadUserInfo() } returns UserInfo(
            gender = Gender.Male,
            age = 20,
            weight = 80f,
            height = 180,
            activityLeve = ActivityLevel.Medium,
            goalType = GoalType.KeepWeight,
            carbRatio = 0.4f, //40%
            proteinRatio = 0.3f,
            fatRatio = 0.3f
        )
        /**
         * So now we say, ok every preferences.loadUserInfo call function here will return this
         * user info object.
         * So now if we use the mockk for our use case, we can very easyly execute the test case without
         * needed to create a special version for that preferences interface.
         */
        calculateMealNutrients = CalculateMealNutrients(preferences)

        /**
         * when we use ``we can specify a function in natural words. Ataully with this we can
         * specify what the fnction will test. For use cases this naming function
         * i ok because you dont call this function
         * Then you can se which test failed and passed be simply seeing this string here
         */
        @Test
        fun `Calories for breakfast properly calculated`() {
            /**Inside here we wanto to call our use case. If we call our use case. We need to provide
             * the list of tracked foods. So want we will do is create a list of track foods
             * Then we will use our use case. i t will sum up all the nutrients like carbs, prot, fat
             * and then after that we need to make suere if thi nutrients were calcualte properly
             * . Taht is the job of the test case*/

            /**So simply create a list of tracked foods. We say we need to have 30 elements and we map this
             * to trak food items. */

            val trackedFoods = (1..30).map {
                TrackedFood(
                    name =  "name",
                    carbs = Random.nextInt(100),
                    protein = Random.nextInt(100),
                    fat = Random.nextInt(100),
                    mealType = MealType.fromString(
                        listOf("breakfast", "lunch", "dinner", "snack").random()
                    ),
                    imageUrl = null,
                    amount = 100,
                    date = LocalDate.now(),
                    calories = Random.nextInt(2000)
                )
            }

            /**Now we have 30 TrackedFood in our list and we wanto to pass this to our
             * use case and then it will return a result as we expected*/

            /**
             * With that one you will call
             */
            val result = calculateMealNutrients(trackedFoods) //como usas invoke puedes llamar a la clase como una funcion pasando el parametro

            /**now we will take this results and find out how many calories we have for the beakfast
             * For that we will take the map that has all the type sof food as key
             * and then filter by the type of food we are interested in.
             * Then sum all the calores that have
             *
             * */


            val breakfastCalories = result.melNutrients.values
                .filter {
                    it.mealType is MealType.BreakFast
                }.sumOf {
                    it.calories
                }

            /**
             * nOw do the same with the list we randomly createt up there
             */

            val expectedClories = trackedFoods
                .filter {
                    it.mealType is MealType.BreakFast
                }.sumOf {
                    it.calories
                }

            /**We wanto to make sure that breakfastCalories that acomes from the real use case
             *  is actually the same of our expected value. Because of then our test will succed. */

            //to say this is equel to this
            assertThat(breakfastCalories).isEqualTo(expectedClories)

        }
    }
}










































































