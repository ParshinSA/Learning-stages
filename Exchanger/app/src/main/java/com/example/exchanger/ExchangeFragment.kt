package com.example.exchanger

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.exchanger.databinding.FragmentAppBinding
import kotlin.math.floor
import kotlin.math.min

class ExchangeFragment : Fragment(R.layout.fragment_app) {

    //viewBinding
    private var _bind: FragmentAppBinding? = null
    private val bind: FragmentAppBinding
        get() = _bind!!

    //    private val viewModel: ViewModelCurrency by viewModels()
    private val myHandler = Handler(Looper.getMainLooper())

    // список возможных валют
    private val allCurrencyType = setOf(
        Currency.EUR,
        Currency.GBP,
        Currency.RUB,
        Currency.USD
    )

    // отношение валюта продажи/валюта покупки
    private var course: Double = 0.0

    // сохраняем предыдущее значение поля "Купить"
    private var previousValueBuy = 0.0
    private var previousValueSell = 0.0

    private var originalBuy = 0.0
    private var originalSell = 0.0

    private var isAddTextUser: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bind = FragmentAppBinding.inflate(inflater, container, false)

        editSellBuy()
        setContextMenu()
        exchangeCurrency()
//        observe()
//        viewModel.getCourse()
        return bind.root
    }

    private fun setContextMenu() {
        registerForContextMenu(bind.typeCurrencyBuy)
        registerForContextMenu(bind.typeCurrencySell)

        bind.typeCurrencyBuy.setOnClickListener {
            bind.typeCurrencyBuy.showContextMenu()
        }
        bind.typeCurrencySell.setOnClickListener {
            bind.typeCurrencySell.showContextMenu()
        }
    }

//    private fun observe() {
//        viewModel.dataBaseCurrency.observe(viewLifecycleOwner) { dbCurrency ->
//            Log.d("ServerServer", "$dbCurrency")
//        }
//    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menu.setHeaderTitle("Тип валюты:")
        allCurrencyType.map { it.name }.forEach { nameTypeCurrency ->
            menu.add(nameTypeCurrency)
                .setOnMenuItemClickListener { item: MenuItem? ->
                    val name = item.toString()
                    val imageRes = allCurrencyType.filter { it.name == name }[0].icon
                    actionOnClick(v, name, imageRes)
                    true
                }
        }
    }

    private fun actionOnClick(view: View?, strRes: String, icRes: Int) {
        fun validateTypeCurrency() {
            bind.btnExchange.isEnabled =
                if (bind.typeCurrencyBuy.text == bind.typeCurrencySell.text) {
                    AlertDialog.Builder(requireContext())
                        .setTitle("Внимание!")
                        .setMessage("При одинаковых типах валют, обмен производиться не будет.")
                        .create()
                        .show()
                    false
                } else true
        }

        when (view?.id) {
            R.id.type_currency_buy -> {
                bind.typeCurrencyBuy.text = strRes
                bind.icCurrencyBuy.setImageResource(icRes)
            }
            R.id.type_currency_sell -> {
                bind.typeCurrencySell.text = strRes
                bind.icCurrencySell.setImageResource(icRes)
            }
        }
        validateTypeCurrency()
        setFieldZero()
    }

    private fun editSellBuy() {
        // слушатели изменения значений(текста) в полях "покупка", "продажа"
        textChangeListener(bind.editBuy)
        textChangeListener(bind.editSell)
    }

    private fun validateValueAndCalculate(field: EditText, userValue: Double) {
        if (userValue == 0.0) {
            setFieldZero()
        } else {
            when (field.id) {
                R.id.edit_buy -> calculateSell(userValue)
                R.id.edit_sell -> {
                    if (userValue > course) {
                        errorMessage(false)
                        calculateBuy(userValue)
                    } else {
                        errorMessage(true)
                        setTextProgram(field, userValue.myToStringRankDouble())
                    }
                }
            }
        }
    }

    private fun textChangeListener(field: EditText) {
        field.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val userValue = s?.toString()?.deleteRank()?.toDoubleOrNull() ?: 0.0
                if (isAddTextUser) {
                    when (field.id) {
                        R.id.edit_buy -> {
                            if (userValue > 999999.0) setTextProgram(field, "999999")
                            previousValueBuy = userValue
                            originalBuy = userValue

                        }
                        R.id.edit_sell -> {
                            if (userValue > 999999999.0) setTextProgram(field, "999999999")
                            previousValueSell = userValue
                            originalSell = userValue
                        }
                    }

                    myHandler.removeCallbacksAndMessages(null)
                    myHandler.postDelayed({
                        getExchangeCourse()
                        when (field.id) {
                            R.id.edit_sell ->
                                setTextProgram(field, userValue.myToStringRankDouble())
                            R.id.edit_buy -> setTextProgram(field, userValue.myToStringRankInt())
                        }
                        validateValueAndCalculate(field, userValue)
                    }, 1000)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }


    private fun errorMessage(boolean: Boolean) {
        if (boolean) {
            bind.erMessageIncorrectValue.visibility = View.VISIBLE
            bind.editSell.mySetTextColor(R.color.red)
            setTextProgram(bind.editBuy, "0")
        } else {
            bind.erMessageIncorrectValue.visibility = View.GONE
            bind.editSell.mySetTextColor(R.color.black)
        }
    }

    private fun calculateBuy(enterValue: Double) {
        originalBuy = min((enterValue / course), 999999.0) //
//        originalBuy = enterValue / course
        val roundValue = floor(originalBuy)
        if (roundValue != previousValueBuy) {
            previousValueBuy = roundValue
            setTextProgram(bind.editBuy, roundValue.myToStringRankInt())
            calculateSell(roundValue)
        }
        if (originalBuy == 999999.0) calculateSell(roundValue) //
    }

    private fun calculateSell(enterValue: Double) {
        originalSell = min((enterValue * course), 999999999.0) //
//        originalSell = enterValue * course
        if (originalSell != previousValueSell) {
            previousValueSell = originalSell
            setTextProgram(bind.editSell, originalSell.myToStringRankDouble())
            calculateBuy(originalSell)
        }
        if (originalSell == 999999999.0) calculateBuy(originalSell) //
    }


    private fun getExchangeCourse() {
        val thread = Thread {
            val currencyBuy = bind.typeCurrencyBuy.text.toString()
            val currencySell = bind.typeCurrencySell.text.toString()

            val bayCourseRelativeBase =
                allCurrencyType.filter { it.name == currencyBuy }.map { it.courseToRub }[0]

            val sellCourseRelativeBase =
                allCurrencyType.filter { it.name == currencySell }.map { it.courseToRub }[0]

            course = bayCourseRelativeBase / sellCourseRelativeBase
        }
        thread.start()
        thread.join()
    }

    private fun setTextProgram(field: EditText, text: String) {
        isAddTextUser = false
        field.setText(text)
        isAddTextUser = true
    }

    private fun setFieldZero() {
        setTextProgram(bind.editBuy, "0")
        setTextProgram(bind.editSell, "0")
        errorMessage(false)
        originalSell = 0.0
        originalBuy = 0.0
        previousValueBuy = 0.0
        previousValueSell = 0.0
    }

    private fun exchangeCurrency() {
        val (typeBuy, typeSell) = Pair(
            bind.typeCurrencyBuy.text.toString(),
            bind.typeCurrencySell.text.toString()
        )

        bind.btnExchange.setOnClickListener {
            val text = bind.logOperation.text.toString()
            bind.logOperation.text = text +
                    "\n" +
                    "\nКуплено $originalBuy $typeBuy," +
                    "\nCтоимость ${originalSell.toBigDecimal()} $typeSell"
        }
    }
}

//    private fun plusPosition(value: String): Int {
//        val number = value.deleteRank().toInt()
//        var counter = 0
//        value.forEach { if (it == ' ') counter++ }
//        return when (number) {
//            in 0..999 -> 0
//            in 1000..999999 -> if (counter == 1) 0 else 1
//            in 1000000..999999999 -> if (counter == 1) 0 else 1
//            in 1000000000..999999999999 -> if (counter == 1) 0 else 1
//            else -> 0
//        }
//    }
