package com.example.testingappkotlin

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import com.example.testingappkotlin.databinding.ActivityProgrammaticallyConstraintBinding
import kotlin.random.Random

class ProgrammaticallyConstraintActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityProgrammaticallyConstraintBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val views = listOf(binding.one,binding.two,binding.three)

        binding.button.setOnClickListener{
            shuffle(views)
        }

    }

    private fun shuffle(views : List<View>) {
        val firstIndex = Random.nextInt(3)
        val secondIndex = firstIndex.inc().mod(3)
        val thirdIndex = secondIndex.inc().mod(3)

        views[firstIndex].updateLayoutParams<ConstraintLayout.LayoutParams> {
            startToStart = ConstraintLayout.LayoutParams.UNSET
            startToEnd = ConstraintLayout.LayoutParams.UNSET
            endToStart = ConstraintLayout.LayoutParams.UNSET
            endToEnd = ConstraintLayout.LayoutParams.UNSET
            startToStart = ConstraintLayout.LayoutParams.PARENT_ID
            endToStart = views[secondIndex].id
        }

        views[secondIndex].updateLayoutParams<ConstraintLayout.LayoutParams> {
            startToStart = ConstraintLayout.LayoutParams.UNSET
            startToEnd = ConstraintLayout.LayoutParams.UNSET
            endToStart = ConstraintLayout.LayoutParams.UNSET
            endToEnd = ConstraintLayout.LayoutParams.UNSET
            startToEnd = views[firstIndex].id
            endToStart = views[thirdIndex].id
        }

        views[thirdIndex].updateLayoutParams<ConstraintLayout.LayoutParams> {
            startToStart = ConstraintLayout.LayoutParams.UNSET
            startToEnd = ConstraintLayout.LayoutParams.UNSET
            endToStart = ConstraintLayout.LayoutParams.UNSET
            endToEnd = ConstraintLayout.LayoutParams.UNSET
            startToEnd = views[secondIndex].id
            endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
        }

    }

}