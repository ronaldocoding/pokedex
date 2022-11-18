package br.com.pokedex.presentation.fragment

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.URLSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.pokedex.R
import br.com.pokedex.databinding.FragmentAboutBinding
import br.com.pokedex.util.zeroNumber

const val CONTACT_US_URL = "mailto:hello@ronaldocosta.dev"
const val TERMS_OF_USE_URL = "https://github.com/ronaldocoding/pokedex"
const val PRIVACY_POLICY_URL = "https://github.com/ronaldocoding/pokedex"

class AboutFragment : Fragment() {

    private val binding: FragmentAboutBinding by lazy {
        FragmentAboutBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpBackButton()
        setUpHyperlinks()
    }

    private fun setUpBackButton() {
        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_aboutFragment_to_homeFragment)
        }
    }

    private fun setUpHyperlinks() {
        binding.apply {
            val contactUsSpan = SpannableString(contactUsText.text)
            contactUsSpan.setSpan(
                URLSpan(CONTACT_US_URL),
                zeroNumber(),
                contactUsText.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            contactUsText.text = contactUsSpan

            val termsOfUseSpan = SpannableString(termsOfUseText.text)
            termsOfUseSpan.setSpan(
                URLSpan(TERMS_OF_USE_URL),
                zeroNumber(),
                termsOfUseText.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            termsOfUseText.text = termsOfUseSpan

            val privacyPolicySpan = SpannableString(privacyPolicyText.text)
            privacyPolicySpan.setSpan(
                URLSpan(PRIVACY_POLICY_URL),
                zeroNumber(),
                privacyPolicyText.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            privacyPolicyText.text = privacyPolicySpan

            contactUsText.movementMethod = LinkMovementMethod.getInstance()
            termsOfUseText.movementMethod = LinkMovementMethod.getInstance()
            privacyPolicyText.movementMethod = LinkMovementMethod.getInstance()
        }
    }

}