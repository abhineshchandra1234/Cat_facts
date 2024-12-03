package com.example.catfacts

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.catfacts.models.CatFacts
import com.example.catfacts.ui.theme.CatFactsTheme
import com.example.catfacts.utils.RetrofitInstance
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class MainActivity : ComponentActivity() {

    private var fact = mutableStateOf(CatFacts())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CatFactsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                        .clickable {
                            sendRequest()
                        },
                    color = MaterialTheme.colorScheme.background
                ) {
                    sendRequest()
//                val context = LocalContext.current
//                //https://catfact.ninja/fact
//                var fact by remember {
//                    mutableStateOf(CatFacts())
//                }
//                val scope = rememberCoroutineScope()
//                LaunchedEffect(key1 = true) {
//                    scope.launch(Dispatchers.IO) {
//                        val response = try {
//                            RetrofitInstance.api.getRandomFact()
//                        }catch (e: HttpException) {
//                            Toast.makeText(context, "http error: ${e.message}", Toast.LENGTH_SHORT).show()
//                            return@launch
//                        }catch (e: IOException) {
//                            Toast.makeText(context, "app error: ${e.message}", Toast.LENGTH_SHORT).show()
//                            return@launch
//                        }
//                        if(response.isSuccessful && response.body()!=null){
//                            withContext(Dispatchers.Main) {
//                                fact = response.body()!!
//                            }
//                        }
//                    }
//                }
                    MyUi(facts = fact)
                }
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun sendRequest() {

        GlobalScope.launch(Dispatchers.IO) {
            val response = try {
                            RetrofitInstance.api.getRandomFact()
                        }catch (e: HttpException) {
                            Toast.makeText(applicationContext, "http error: ${e.message}", Toast.LENGTH_SHORT).show()
                            return@launch
                        }catch (e: IOException) {
                            Toast.makeText(applicationContext, "app error: ${e.message}", Toast.LENGTH_SHORT).show()
                            return@launch
                        }
                        if(response.isSuccessful && response.body()!=null){
                            withContext(Dispatchers.Main) {
                                fact.value = response.body()!!
                            }
                        }
        }
    }
}

@Composable
fun MyUi(facts: MutableState<CatFacts>, modifier: Modifier= Modifier) {
    Column(modifier.fillMaxSize()
        .padding(16.dp),
        verticalArrangement = Arrangement.Center) {
        Text(text = "Cat Facts:", modifier.padding(bottom = 25.dp),fontSize = 26.sp)
        Text(text = facts.value.fact, fontSize = 26.sp, fontWeight = FontWeight.Bold, lineHeight = 40.sp)
    }
}

//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    CatFactsTheme {
//        Greeting("Android")
//    }
//}