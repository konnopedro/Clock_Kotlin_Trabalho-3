⏰ Tun Tun Clock — Trabalho 2

Aplicativo de relógio desenvolvido em Kotlin utilizando Jetpack Compose para a disciplina de Desenvolvimento de Aplicativos Móveis.

Este projeto é uma evolução do aplicativo desenvolvido no Trabalho 1, adicionando navegação real, listas dinâmicas, cadastro e remoção de itens e telas de detalhes.

📱 Sobre o projeto

O Tun Tun Clock possui três funcionalidades principais:

🕐 Relógio
⏱️ Cronômetro
⏳ Timer

No Trabalho 2, o aplicativo foi ampliado com:

⏰ Gerenciamento de alarmes;
🌎 Gerenciamento de fusos horários;
📋 Listas com LazyColumn, Card e mutableStateListOf;
➕ Adição de novos itens;
🗑️ Remoção de itens;
🔎 Telas de detalhes;
✏️ Edição de alarmes;
🧭 Navegação com NavigationBar, NavController e NavHost;
🔙 Botões de voltar utilizando navController.popBackStack().
🧭 Navegação

O aplicativo possui uma NavigationBar para as principais áreas:

Relógio | Cronômetro | Timer


A partir da tela de Relógio também é possível acessar:

Alarmes
 └── Detalhes do Alarme

Fusos Horários
 └── Detalhes do Fuso


As rotas são organizadas através do objeto Rotas e controladas pelo Navigation Compose.

⏰ Alarmes

A tela de Alarmes permite:

Adicionar novos alarmes;
Visualizar os alarmes cadastrados;
Remover alarmes;
Abrir os detalhes de um alarme;
Editar as informações;
Ativar ou desativar um alarme.

Os alarmes são armazenados em uma lista reativa utilizando mutableStateListOf.

🌎 Fusos Horários

A tela de Fusos Horários permite:

Adicionar uma nova localidade;
Informar cidade, país e zona;
Visualizar os fusos cadastrados;
Remover fusos;
Abrir os detalhes de um fuso.

Na tela de detalhes são exibidos os horários da localidade e de São Paulo, além da diferença entre os horários.

✨ Complexidade adicional

As telas de detalhes possuem funcionalidades além da simples exibição dos dados.

Nos Detalhes do Alarme, é possível editar as informações do item diretamente.

Nos Detalhes do Fuso, o aplicativo calcula a diferença de horário em relação a São Paulo e apresenta o horário atual da localidade.

📚 Documentação do Trabalho 2
1. 🔄 Evolução do Trabalho 1

No Trabalho 1, o aplicativo possuía as telas de Relógio, Cronômetro e Timer, com uma interface inicial e botões de navegação.

Para o Trabalho 2, essas telas foram mantidas e evoluídas, passando a fazer parte de uma navegação real. Também foram adicionadas as telas de Alarmes, Detalhes do Alarme, Fusos Horários e Detalhes do Fuso.

As principais mudanças foram:

Implementação do NavigationBar;
Criação do NavHost;
Organização das rotas;
Criação de duas listas dinâmicas;
Cadastro e remoção de itens;
Criação das telas de detalhes;
Passagem de dados entre as telas;
Edição de alarmes;
Cálculo de diferença entre fusos horários.


2. 📱 Novas telas e suas funções
Alarmes

A tela permite cadastrar, visualizar, remover e editar alarmes.

Detalhes do Alarme

Mostra as informações do alarme selecionado e permite editar seus dados.

Fusos Horários

Permite cadastrar, visualizar e remover diferentes localidades.

Detalhes do Fuso

Mostra as informações do fuso selecionado, seu horário atual e sua diferença em relação a São Paulo.

As telas foram escolhidas porque estão diretamente relacionadas ao tema principal do aplicativo: controle e acompanhamento de horários.

📸 Prints das telas

<img width="960" height="1280" alt="1f65c36e-ed91-4f1c-bd55-79799e36dc1e" src="https://github.com/user-attachments/assets/59e58dba-9202-44cb-8532-4901cd9b5803" />
<img width="960" height="1280" alt="88bc5084-33ed-4ad0-af62-e35a91328b4a" src="https://github.com/user-attachments/assets/4dfa48d4-456a-4a10-9353-cda6ffb2aa18" />
<img width="960" height="1280" alt="7bb38787-ac14-40f8-87ed-9fb4165285be" src="https://github.com/user-attachments/assets/5ab0ddfb-0e04-4c59-971c-c5fdd86237e0" />
<img width="960" height="1280" alt="42806b45-8251-46c8-bf28-bda749c9ef87" src="https://github.com/user-attachments/assets/08ba4975-10a6-4ac0-8dc6-d1b0d07dac11" />


3. 🏗️ Organização do código e navegação

A navegação foi centralizada no arquivo AppNavigation.kt.

Foi criado um objeto Rotas para organizar os caminhos utilizados pelo aplicativo.

As listas de Alarmes e Fusos Horários são mantidas no nível da navegação utilizando mutableStateListOf. Dessa forma, as telas de lista e de detalhes conseguem trabalhar com os mesmos dados.

Para abrir os detalhes, o ID do item selecionado é enviado através da rota. A tela de detalhes utiliza esse ID para localizar o item correto.

Essa organização foi escolhida para facilitar a comunicação entre as telas e manter a navegação centralizada.

📸 Registro da navegação
[Screen_recording_20261008_101007.webm](https://github.com/user-attachments/assets/619351e2-493f-4fd2-b30a-3472f4be4813)

4. ⭐ Complexidade extra

O requisito do Trabalho 2 determina que pelo menos uma tela de detalhes faça algo além de simplesmente mostrar os campos do item.

No projeto, foram utilizadas duas funcionalidades adicionais:

Detalhes do Alarme: permite editar as informações do alarme diretamente na tela e salvar as alterações.

Detalhes do Fuso: utiliza os dados do fuso para calcular a diferença de horário em relação a São Paulo e apresentar o horário atual da localidade.

Essa escolha foi feita para que as telas de detalhes tivessem uma função real dentro do aplicativo, em vez de apenas repetir os dados cadastrados.

📸 Registro da funcionalidade
[Screen_recording_20261008_101330.webm](https://github.com/user-attachments/assets/dac878b4-c84e-4bcd-86bc-283f5d025bb9)

5. 🧑‍💻 Dificuldades encontradas

Durante o desenvolvimento, os integrantes não encontraram dificuldades relacionadas à implementação das novas funcionalidades.


🛠️ Tecnologias utilizadas
Kotlin
Android Studio
Jetpack Compose
Material 3
Navigation Compose
AndroidX
Gradle
📂 Estrutura principal
app/
└── src/
    └── main/
        ├── java/com/example/tungclock/
        │   ├── MainActivity.kt
        │   ├── AppNavigation.kt
        │   ├── RelogioScreen.kt
        │   ├── CronometroScreen.kt
        │   ├── TimerScreen.kt
        │   ├── AlarmesScreen.kt
        │   ├── DetalheAlarmeScreen.kt
        │   ├── FusosScreen.kt
        │   └── DetalheFusoScreen.kt
        │
        └── res/

🚀 Como executar
Pré-requisitos
Android Studio;
JDK 17;
Android SDK;
Emulador Android ou dispositivo físico.
Clone o repositório
git clone https://github.com/konnopedro/Clock_Kotlin_Trabalho-2.git


Abra o projeto no Android Studio, aguarde a sincronização do Gradle e execute o aplicativo em um dispositivo ou emulador Android.

👥 Integrantes
Daniel Luiz — Relógio
Daniel Rocha — Cronômetro
Pedro Konno — Timer
🎓 Disciplina

Desenvolvimento de Aplicativos Móveis

Trabalho 2 — MAF (Mínimo Aplicativo Funcional)

Projeto desenvolvido utilizando Kotlin + Jetpack Compose.
