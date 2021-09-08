package com.example.exchanger

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.exchanger.databinding.FragmentAppBinding
import kotlin.math.floor
import kotlin.math.min

class ExchangeFragment : Fragment(R.layout.fragment_app) {

    //viewBinding
    private var _bind: FragmentAppBinding? = null
    private val bind: FragmentAppBinding
        get() = _bind!!

    private val viewModel: ViewModelCurrency by viewModels()
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

    // предыдущее значения
    private var previousValueBuy = 0.0
    private var previousValueSell = 0.0

    // оригинальные значения
    private var originalBuy = 0.0
    private var originalSell = 0.0

    // флаг, кем устанавливается значение программа=false / пользователь=true
    private var isAddTextUser: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bind = FragmentAppBinding.inflate(inflater, container, false)

        editSellBuy()
        setContextMenu()
        logOperation()
        observe()
        viewModel.getCourse()
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

    private fun observe() {
        viewModel.dataBaseCurrency.observe(viewLifecycleOwner) { dbCurrency ->
            Log.d("ServerServer", "$dbCurrency")
        }
    }

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


    private fun textChangeListener(field: EditText) {
        field.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // сохраняем и преводим обратно в число любое введенное значение
                var numberValue = s?.toString()?.deleteRank()?.toDoubleOrNull() ?: 0.0
                // если вводит пользователь
                if (isAddTextUser) {
                    bind.btnExchange.isEnabled = false
                    // сохраняем ввод пользователя
                    val userValue = s?.toString() ?: "0"
                    // получаем текушее положение курсора, при вводе пользователя
                    val currentSelectionPos = field.selectionStart
                    // проверяем по огранечению, модифицируем, сохраняем и устанавливаем значение
                    when (field.id) {
                        R.id.edit_buy -> {
                            numberValue = min(numberValue, 999999.0) // огранечение
                            previousValueBuy = numberValue
                            originalBuy = numberValue // сохранение оригинальное значение
                            // модифицируем и устанавливаем програмно с блокиратором
                            setTextProgram(field, numberValue.myToStringRankInt())
                        }
                        R.id.edit_sell -> {
                            numberValue = min(numberValue, 999999999.0)
                            previousValueSell = numberValue
                            originalSell = numberValue // сохранение оригинальное значение
                            // модифицируем и устанавливаем програмно с блокиратором
                            setTextProgram(field, numberValue.myToStringRankDouble())
                        }
                    }
                    // устанавливаем курсор
                    field.mySetSelection(currentSelectionPos, userValue)

                    // убиваем запущенные процессы(таймеры), запускаем новый
                    myHandler.removeCallbacksAndMessages(null)
                    myHandler.postDelayed({
                        // запрос курса
                        getExchangeCourse()
                        // проверка введенного значения, отправка на расчет
                        validateValueAndCalculate(field, numberValue)
                        bind.btnExchange.isEnabled = true
                    }, 1000)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    // проверка введенного значения, отправка на расчет
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

    // сообщение о вводе значения меньше минимума
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

    // расчет поля значения покупки
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

    // расчет поля значения продажи
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

    // запрос курса
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

    // установка текста(значение) в EditText,
    // указываем, что устанавливает не пользователь
    private fun setTextProgram(field: EditText, text: String) {
        isAddTextUser = false
        field.setText(text)
        isAddTextUser = true
    }

    // обнуление значений и полей
    private fun setFieldZero() {
        setTextProgram(bind.editBuy, "0")
        setTextProgram(bind.editSell, "0")
        errorMessage(false)
        originalSell = 0.0
        originalBuy = 0.0
        previousValueBuy = 0.0
        previousValueSell = 0.0
    }

    // фиксируем выполненные операции
    private fun logOperation() {
        val (typeBuy, typeSell) = Pair(
            bind.typeCurrencyBuy.text.toString(),
            bind.typeCurrencySell.text.toString()
        )

        bind.btnExchange.setOnClickListener {
            val text = bind.logOperation.text.toString()
            bind.logOperation.text = text +
                    "\n" +
                    "\nКуплено ${originalBuy.myToStringRankInt()} $typeBuy," +
                    "\nCтоимость ${originalSell.myToStringRankDouble()} $typeSell"
        }
    }


}


