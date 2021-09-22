package com.example.myapplication

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.myapplication.currency.CurrencyApp
import com.example.myapplication.databinding.FragmentAppBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.min

class ExchangeFragment : Fragment(R.layout.fragment_app) {

    //viewBinding
    private var _bind: FragmentAppBinding? = null
    private val bind: FragmentAppBinding
        get() = _bind!!

    private val viewModel: ViewModelCurrency by viewModels()
    private val myHandler = Handler(Looper.getMainLooper())

    // флаг, кем устанавливается значение программа=false / пользователь=true
    private var isAddTextUser: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bind = FragmentAppBinding.inflate(inflater, container, false)

        setContextMenu()
        textChange()
        observeData()
        startedGetCourse()
        logOperation()
        bind.btnExchange.isEnabled = false

        return bind.root
    }

    private fun startedGetCourse() {
        // запрос курса при старте
        viewModel.getCourse(
            bind.typeCurrencyBuy.text.toString(),
            bind.typeCurrencySell.text.toString()
        )
    }

    private fun textChange() {
        textChangeListener(bind.editSell)
        textChangeListener(bind.editBuy)
    }

    private fun observeData() {
        viewModel.dataBuy.observe(viewLifecycleOwner) { valuesBuy ->
            if (valuesBuy != 0.0) {
                setTextProgram(
                    bind.editBuy,
                    valuesBuy.myToStringRankInt()
                )
                bind.btnExchange.isEnabled = true
            } else {
                clearField()
            }
        }

        viewModel.dataSell.observe(viewLifecycleOwner) { valuesSell ->
            if (valuesSell != 0.0) {
                setTextProgram(
                    bind.editSell,
                    valuesSell.myToStringRankDouble()
                )
                bind.btnExchange.isEnabled = true
            } else {
                clearField()
            }
        }

        viewModel.incorrectValueSell.observe(viewLifecycleOwner) {
            errorMessageMinimalPrice(it)
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        activatingView: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menu.setHeaderTitle("Тип валюты:")
        CurrencyApp.values().forEach { currency ->
            menu.add(currency.name)
                .setOnMenuItemClickListener { menuItem: MenuItem? ->
                    // получаем имя кнопки = тип валюты
                    val typeCurrency = menuItem.toString()
                    // находим логотип по имени
                    val logotypeCurrency =
                        CurrencyApp.values().filter { it.name == typeCurrency }[0].logotype
                    // отправляем на установку тип и логотип
                    setLogotypeAndTypeCurrency(typeCurrency, activatingView, logotypeCurrency)
                    // проверяем тип, ели одинаковый блокируем кнопку обмена
                    verifyCurrencyType()
                    true
                }
        }
    }

    private fun verifyCurrencyType() {
        bind.btnExchange.isEnabled =
            if (bind.typeCurrencyBuy.text == bind.typeCurrencySell.text) {
                AlertDialog.Builder(requireContext())
                    .setTitle("Внимание!")
                    .setMessage("При одинаковых типах валют, обмен производиться не будет.")
                    .create()
                    .show()
                false
            } else {
                viewModel.getCourse(
                    bind.typeCurrencyBuy.text.toString(),
                    bind.typeCurrencySell.text.toString()
                )
                clearField()
                true
            }
    }

    private fun setLogotypeAndTypeCurrency(
        typeCurrency: String,
        activatingView: View?,
        logotypeCurrency: Int
    ) {
        when (activatingView?.id) {
            R.id.type_currency_buy -> {
                bind.typeCurrencyBuy.text = typeCurrency
                bind.icCurrencyBuy.setImageResource(logotypeCurrency)
            }
            R.id.type_currency_sell -> {
                bind.typeCurrencySell.text = typeCurrency
                bind.icCurrencySell.setImageResource(logotypeCurrency)
            }
        }
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

    private fun textChangeListener(field: EditText) {
        field.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                inputAnalysis(s, field)
            }
        })
    }

    private fun inputAnalysis(s: Editable?, field: EditText) {
        // если вводит пользователь
        if (isAddTextUser) {
            // если поле пустое или равно нулю, зануляем все поля
            if (validateUserValue(s)) {
                // на момент расчетов и трансформации чисел блокируем кнопку
                bind.btnExchange.isEnabled = false
                // сохраняем ввод пользователя до изменения строки программой
                val userValue = s.toString()
                // получаем текушее положение курсора, при вводе пользователя установки курсора
                val currentSelectionPos = field.selectionStart
                // проверяем по огранечению, модифицируем и устанавливаем значение обратно в поле
                // получаем скорректированное число для вычисления
                val userValueCorrection = changingUserInput(field, userValue)
                // устанавливаем курсор
                field.mySetSelection(currentSelectionPos, userValue)

                // убиваем запущенные процессы(таймеры), запускаем новый
                myHandler.removeCallbacksAndMessages(null)
                myHandler.postDelayed({
                    viewModel.getCourse(
                        typeCurrencyBuy = bind.typeCurrencyBuy.text.toString(),
                        typeCurrencySell = bind.typeCurrencySell.text.toString(),
                    )
                    // расчет обмена
                    viewModel.calculateExchange(
                        field = field,
                        userValue = userValueCorrection
                    )
                }, 1000)
            }
        }
    }

    private fun clearField() {
        isAddTextUser = false
        bind.editSell.setText("")
        bind.editBuy.setText("")
        isAddTextUser = true
        errorMessageMinimalPrice(false)
        bind.btnExchange.isEnabled = false
        myHandler.removeCallbacksAndMessages(null)
    }

    private fun validateUserValue(userValue: Editable?): Boolean {
        return if (
            userValue != null
            && userValue.isNotEmpty()
            && userValue.toString().deleteRank().toDouble() != 0.0
        ) {
            true
        } else {
            clearField()
            false
        }
    }

    private fun changingUserInput(field: EditText, userValue: String): Double {
        var numberValue = userValue.deleteRank().toDouble()
        when (field.id) {
            R.id.edit_buy -> {
                numberValue = min(numberValue, 999999.0) // огранечение
                // модифицируем и устанавливаем програмно с блокиратором
                setTextProgram(field, numberValue.myToStringRankInt())
            }
            R.id.edit_sell -> {
                numberValue = min(numberValue, 999999999.0)
                // модифицируем и устанавливаем програмно с блокиратором
                setTextProgram(field, numberValue.myToStringRankDouble())
            }
        }
        return numberValue
    }

    private fun setTextProgram(field: EditText, text: String) {
        isAddTextUser = false
        field.setText(text)
        isAddTextUser = true
    }

    // сообщение о вводе значения меньше минимума
    private fun errorMessageMinimalPrice(boolean: Boolean) {
        if (boolean) {
            bind.erMessageIncorrectValue.visibility = View.VISIBLE
            bind.editSell.mySetTextColor(R.color.red)
            bind.btnExchange.isEnabled = !boolean
        } else {
            bind.erMessageIncorrectValue.visibility = View.GONE
            bind.editSell.mySetTextColor(R.color.black)
            bind.btnExchange.isEnabled = !boolean
        }
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
                    "\n${SimpleDateFormat("dd/M/yyyy HH:mm:ss").format(Date())}" +
                    "\nКуплено ${
                        viewModel.dataBuy.value?.toDouble()?.myToStringRankInt()
                    } $typeBuy," +
                    "\nCтоимость ${
                        viewModel.dataSell.value?.toDouble()?.myToStringRankDouble()
                    } $typeSell"
        }
    }
}