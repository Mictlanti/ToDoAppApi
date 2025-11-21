package com.example.todoapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.todoapp.presentation.viewmodels.ToDoViewmodel
import com.example.todoapp.presentation.views.CreateNotesRoute
import com.example.todoapp.presentation.views.EditNotesRoute
import com.example.todoapp.presentation.views.HomeScreenRoute

@Composable
fun AppScreens(viewmodel: ToDoViewmodel) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.HomeRoute
    ) {
        composable<Routes.HomeRoute> {
            HomeScreenRoute(
                viewmodel = viewmodel,
                onClickAddNote = {
                    navController.navigate(Routes.CreatedNotes)
                },
                onClickEdit = { idDoc ->
                    navController.navigate(Routes.EditNote(idDoc))
                }
            )
        }

        composable<Routes.CreatedNotes> {
            CreateNotesRoute(
                viewModel = viewmodel,
                onNavClick = { navController.navigate(Routes.HomeRoute) }
            )
        }

        composable<Routes.EditNote> { backStackEntry ->

            val args = backStackEntry.toRoute<Routes.EditNote>()

            EditNotesRoute(
                viewModel = viewmodel,
                id = args.id,
                onNavClick = { navController.popBackStack() }
            )
        }

    }
}