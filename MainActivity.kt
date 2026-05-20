package com.exemplo.oficinaseucido // Verifique se o pacote bate com o seu projeto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Cores Oficiais da Oficina (Estilo Dark / Mecânica)
val FundoEscuro = Color(0xFF121212)
val CardEscuro = Color(0xFF1E1E1E)
val LaranjaMecanica = Color(0xFFFF9800)
val Branco = Color(0xFFFFFFFF)
val CinzaTexto = Color(0xFFB3B3B3)

data class Agendamento(
    val id: Int,
    val cliente: String,
    val carro: String,
    val dataHora: String,
    val servico: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                OficinaCidoScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OficinaCidoScreen() {
    // Campos do Formulário
    var nomeCliente by remember { mutableStateOf("") }
    var modeloCarro by remember { mutableStateOf("") }
    var dataAgendada by remember { mutableStateOf("") }
    var descricaoServico by remember { mutableStateOf("") }

    // Lista de Agendamentos salvos na memória
    val listaAgendamentos = remember { mutableStateListOf<Agendamento>() }
    var contadorId by remember { mutableStateOf(1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FundoEscuro)
            .padding(16.dp)
    ) {
        // Cabeçalho do Aplicativo
        Text(
            text = "Oficina do Seu Cido - Agenda",
            color = LaranjaMecanica,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Formulário para Cadastrar Novo Agendamento
        Text("Agendar Novo Veículo", color = Branco, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = nomeCliente,
            onValueChange = { nomeCliente = it },
            label = { Text("Nome do Cliente", color = CinzaTexto) },
            colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = LaranjaMecanica, textColor = Branco),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = modeloCarro,
            onValueChange = { modeloCarro = it },
            label = { Text("Carro (Modelo e Placa)", color = CinzaTexto) },
            colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = LaranjaMecanica, textColor = Branco),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = dataAgendada,
            onValueChange = { dataAgendada = it },
            label = { Text("Data e Horário (Ex: Amanhã 14h)", color = CinzaTexto) },
            colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = LaranjaMecanica, textColor = Branco),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = descricaoServico,
            onValueChange = { descricaoServico = it },
            label = { Text("Serviço (Ex: Trocar Freio / Revisão)", color = CinzaTexto) },
            colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = LaranjaMecanica, textColor = Branco),
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
        )

        // Botão para Salvar
        Button(
            onClick = {
                if (nomeCliente.isNotEmpty() && modeloCarro.isNotEmpty() && dataAgendada.isNotEmpty()) {
                    listaAgendamentos.add(
                        Agendamento(
                            id = contadorId++,
                            cliente = nomeCliente,
                            carro = modeloCarro,
                            dataHora = dataAgendada,
                            servico = descricaoServico
                        )
                    )
                    // Limpa as caixas de texto após salvar
                    nomeCliente = ""
                    modeloCarro = ""
                    dataAgendada = ""
                    descricaoServico = ""
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = LaranjaMecanica),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Confirmar Agendamento", color = FundoEscuro, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Lista de Carros Agendados na Tela
        Text("Carros Agendados para Entrada", color = Branco, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(listaAgendamentos) { vaga ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = CardEscuro),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Cliente: ${vaga.cliente}", color = Branco, fontWeight = FontWeight.Bold)
                            Text(vaga.dataHora, color = LaranjaMecanica, fontWeight = FontWeight.Bold)
                        }
                        Text("Veículo: ${vaga.carro}", color = CinzaTexto)
                        Text("Serviço planejado: ${vaga.servico}", color = CinzaTexto)
                    }
                }
            }
        }
    }
}
