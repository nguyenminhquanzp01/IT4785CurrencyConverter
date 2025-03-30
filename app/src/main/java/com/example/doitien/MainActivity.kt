package com.example.doitien

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    enum class Currency(val rate: Double) {
        USD(1.0),
        VND(25550.0),
        Euro(0.92),
        Peso(57.36),
        Pound(0.7724)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout)
        val currencyType: Array<String> = arrayOf("VND", "USD", "Euro", "Peso", "Pound")

        val fromSpinner: Spinner = findViewById(R.id.fromSpinner)
        val toSpinner: Spinner = findViewById(R.id.toSpinner)
        val fromText: EditText = findViewById(R.id.fromText)
        val toText: TextView = findViewById(R.id.toText)
        val rateText: TextView = findViewById(R.id.rateText)
        var currentFromSelection: Currency = Currency.VND
        var currentToSelection: Currency = Currency.VND
        var currentFromValue: Double
        var currentToValue: Double

        val spinnerAdapter: ArrayAdapter<String> = ArrayAdapter(
            this,
            R.layout.currency_type_layout,
            R.id.item,
            currencyType
        )
        fromSpinner.adapter = spinnerAdapter
        toSpinner.adapter = spinnerAdapter

        fromSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                currentFromSelection = Currency.valueOf(currencyType[position])
                rateText.text = String.format("Rate:\n1 %s = %.4f %s", currentFromSelection.name, currentToSelection.rate/currentFromSelection.rate, currentToSelection.name)
                val s = fromText.text.toString()
                currentFromValue = if (s.isBlank()) 0.0 else s.toDouble()
                currentToValue = currentFromValue / currentFromSelection.rate * currentToSelection.rate
                toText.text = String.format("%.3f", currentToValue)
            }
        }
        toSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                currentToSelection = Currency.valueOf(currencyType[position])
                rateText.text = String.format("Rate:\n1 %s = %.4f %s", currentFromSelection.name, currentToSelection.rate/currentFromSelection.rate, currentToSelection.name)
                val s = fromText.text.toString()
                currentFromValue = if (s.isBlank()) 0.0 else s.toDouble()
                currentToValue = currentFromValue / currentFromSelection.rate * currentToSelection.rate
                toText.text = String.format("%.3f", currentToValue)
            }
        }
        fromText.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //nothing to do
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                currentFromValue = if (s.toString().isBlank()) 0.0 else s.toString().toDouble()
                //or use String.isEmpty\
                currentToValue = currentFromValue / currentFromSelection.rate * currentToSelection.rate
                toText.text = String.format("%.3f", currentToValue)
            }
            override fun afterTextChanged(s: Editable?) {

            }

        })

    }
}

