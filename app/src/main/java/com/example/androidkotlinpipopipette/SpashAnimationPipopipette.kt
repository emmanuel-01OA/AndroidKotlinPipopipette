package com.example.androidkotlinpipopipette

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class SpashAnimationPipopipette : AppCompatActivity() {

    private lateinit var imgAnims : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spash_animation_pipopipette)

        imgAnims = findViewById(R.id.imgAnim)
        imgAnims.alpha=0f
        imgAnims.animate().setDuration(1500).alpha(1f).withEndAction{

            val intent : Intent = Intent(this, MainActivity::class.java);
            this.startActivity(intent)
            overridePendingTransition(androidx.appcompat.R.anim.abc_fade_in, androidx.appcompat.R.anim.abc_fade_out)
            finish()
        }


    }
}