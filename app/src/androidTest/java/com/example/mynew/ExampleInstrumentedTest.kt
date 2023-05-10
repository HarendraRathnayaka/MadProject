package com.example.mynew

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mynew.activities.AddCompany
import com.example.mynew.models.CompanyModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.mynew", appContext.packageName)
    }

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(AddCompany::class.java)
    private lateinit var activityScenario: ActivityScenario<AddCompany>
    @Before
    fun setUp() {
        activityScenario = activityScenarioRule.scenario
        activityScenario.onActivity { activity ->
            // Do any setup you need for your activity here
        }
    }


    @Test
    fun testSaveCompanyData() {

        // Simulate user input
        onView(withId(R.id.company_name)).perform(typeText("My Company"))
        onView(withId(R.id.company_industry)).perform(typeText("Tech"))
        onView(withId(R.id.company_vacancies)).perform(typeText("hello"))
        onView(withId(R.id.company_no)).perform(typeText("0234567890"))
        onView(withId(R.id.company_mail)).perform(typeText("sadeepalakshan0804@gmail.com"))
        onView(withId(R.id.company_address)).perform(typeText("123 Main St"))

        onView(withId(R.id.btnsavecompany)).perform(click())

        // Wait for save to complete
        Thread.sleep(5000)

        // Verify data was saved correctly
        val dbRef = FirebaseDatabase.getInstance().getReference("companies")
        val query = dbRef.orderByChild("companyName").equalTo("My Company")
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                assertEquals(1, snapshot.childrenCount)

                val company = snapshot.children.first().getValue(CompanyModel::class.java)
                assertEquals("My Company", company?.companyName)
                assertEquals("Tech", company?.companyIndustry)
                assertEquals("hello", company?.companyVacancies)
               assertEquals("0234567890", company?.companyNo)
                assertEquals("sadeepalakshan0804@gmail.com", company?.companyMail)
                assertEquals("123 Main St", company?.companyAddress)

            }

            override fun onCancelled(error: DatabaseError) {
                fail("Database error: ${error.message}")
            }
        })

        //scenario.close()
    }

    @After
    fun tearDown() {
        activityScenario.close()

    }
}