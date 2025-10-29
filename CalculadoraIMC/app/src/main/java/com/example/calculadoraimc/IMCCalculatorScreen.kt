package com.example.calculadoraimc

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.calculadoraimc.ui.theme.CalculadoraIMCTheme

fun calcularIMC(peso: Float, altura: Float): Pair<String, String> {
    if (altura == 0f || peso == 0f) {
        return Pair("N/A", "Nenhuma.")
    }

    val imc = peso / (altura * altura)

    val classificacao = when {
        imc < 17 -> "Muito abaixo do peso"
        imc < 18.5 -> "Abaixo do peso"
        imc < 25 -> "Peso normal"
        imc < 30 -> "Acima do peso"
        imc < 35 -> "Obesidade I"
        imc < 40 -> "Obesidade II (severa)"
        else -> "Obesidade III (mórbida)"
    }

    val imcFormatado = "%.2f".format(imc)
    return Pair(imcFormatado, classificacao)
}

@Composable
fun IMCCalculatorScreen() {
    // Variáveis para guardar o texto digitado (Peso e Altura)
    var pesoInput by remember { mutableStateOf("") }
    var alturaInput by remember { mutableStateOf("") }

    // Variáveis para guardar o resultado do cálculo
    var imcResult by remember { mutableStateOf("0.00") }
    var classificacaoResult by remember { mutableStateOf("Nenhuma.") }


    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp), // Adiciona um padding ao redor de toda a tela
        horizontalAlignment = Alignment.CenterHorizontally // Centraliza horizontalmente o conteúdo
    ) {
        // Título
        Text(
            text = "Calculadora de IMC",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Input de Peso
        OutlinedTextField(
            value = pesoInput,
            onValueChange = { pesoInput = it.replace(",", ".") }, // Substitui vírgula por ponto
            label = { Text("Peso (kg)") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp), // Ocupa a largura máxima disponível
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number) // Teclado numérico
        )

        // Input de Altura
        OutlinedTextField(
            value = alturaInput,
            onValueChange = { alturaInput = it.replace(",", ".") },
            label = { Text("Altura (m)") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        // Botão de Cálculo
        Button(
            // Ação ao clicar no botão
            onClick = {
                // Converte os textos para Float
                val pesoFloat = pesoInput.toFloatOrNull() ?: 0f
                val alturaFloat = alturaInput.toFloatOrNull() ?: 0f

                val (imc, classificacao) = calcularIMC(pesoFloat, alturaFloat)

                // Atualiza o estado dos resultados
                imcResult = imc
                classificacaoResult = classificacao
            },
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text("CALCULAR IMC", fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Resultado
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp).fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Seu IMC é:",
                    fontSize = 20.sp
                )
                Text(
                    text = imcResult,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                ) {
                    Text(
                        text = "Classificação: ",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal
                    )
                    Text(
                        text = classificacaoResult,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IMCCalculatorPreview() {
    CalculadoraIMCTheme {
        IMCCalculatorScreen()
    }
}