package com.example.biometricauthentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.biometricauthentication.databinding.FragmentBiomartBinding
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.Executor


class BiomartFragment : Fragment() {

    lateinit var binding: FragmentBiomartBinding
    lateinit var executor: Executor
    lateinit var biometricPrompt: BiometricPrompt
    lateinit var promptInfo: BiometricPrompt.PromptInfo



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBiomartBinding.inflate(inflater)

                executor = ContextCompat.getMainExecutor(requireContext())
                biometricPrompt = BiometricPrompt(requireActivity(), executor, object : BiometricPrompt.AuthenticationCallback(){

                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)

                        binding.authText.text = "Authentication Error: $errString "
                        Toast.makeText(requireContext(), "Authentication Error: $errString", Toast.LENGTH_LONG).show()
                    }

                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        Toast.makeText(requireContext(), "Authentication Successful", Toast.LENGTH_LONG).show()
                        findNavController().navigate(R.id.action_biomartFragment_to_profile)
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        Toast.makeText(requireContext(), "Authentication Failed", Toast.LENGTH_LONG).show()
                    }
                })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biomertic Authentication")
            .setSubtitle("Use Biometric Authentication")
            .setNegativeButtonText("Use Device Password Instead")
            .build()

        binding.authBtn.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }



        return binding.root




    }
}
