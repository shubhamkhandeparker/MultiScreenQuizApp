package com.shubham.multiscreenquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shubham.multiscreenquiz.ui.theme.MultiScreenQuizTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MultiScreenQuizTheme {
                //Our entire app's navigation will be managed here
                AppNavigation()
            }
        }
    }
}

//This is our main navigation composable

@Composable
fun AppNavigation() {
    //1.create the NavController
    val navController = rememberNavController()

    val quizViewModel: QuizViewModel= viewModel ()



    //2.Create the NavHost container
    NavHost(
        navController = navController,
        startDestination = "start_screen" //3.Tell it which screen to show first

    ) {
        //4.Define the "Start" screen
        composable(route = "start_screen")
        {
            StartScreen(navController = navController,
                viewModel = quizViewModel)
        }

        //5.Define the "Quiz" screen
        composable(route = "quiz_screen")
        {
            QuizScreen(navController = navController,
                viewModel = quizViewModel)
        }

        //6.Define the "Result" screen
        composable(route = "result_screen")
        {
            ResultScreen(navController = navController,
                viewModel = quizViewModel)
        }

    }
}

@Composable
fun StartScreen(navController: NavController,viewModel: QuizViewModel) {
    //We'll use a column to center the button on the screen
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome to the Quiz!",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))


        Button(onClick = {
            viewModel.resetQuiz()
            navController.navigate("quiz_screen")
        }) {

            Text(text = "Start Quiz", fontSize = 20.sp)
        }
    }

}

@Composable
fun QuizScreen(navController: NavController,viewModel: QuizViewModel) {
    LaunchedEffect(viewModel.quizFinished) {

        if(viewModel.quizFinished){
            navController.navigate("result_screen")
        }
    }

   val currentQuestion = viewModel.currentQuestion
    val selectedOption = viewModel.selectedOptionIndex



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = currentQuestion.text,
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(24.dp))

        currentQuestion.options.forEachIndexed { index, option ->
            Button(
                onClick = {
                   viewModel.selectAnswer(index)
                },
                modifier = Modifier.fillMaxWidth()
            )
            {
                Text(text = option)
            }
            Spacer(modifier = Modifier.height(8.dp))

        }
        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                viewModel.nextQuestionOrFinish()
            },
            enabled = selectedOption != -1,
            modifier = Modifier.fillMaxWidth()
        )
        {
            Text(text = "Next", fontSize = 20.sp)
        }

    }
}

@Composable
fun ResultScreen(navController: NavController,viewModel: QuizViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Text(text ="Your Score ${viewModel.score} / 3" ,
            style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier =Modifier.height(16.dp))

        Button(onClick = {
            viewModel.resetQuiz()
            navController.navigate("start_screen")
        })
        {
            Text("Play Again!")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StartScreenPreview() {
    MultiScreenQuizTheme {
        StartScreen(navController = rememberNavController(),viewModel = viewModel ())
    }
}