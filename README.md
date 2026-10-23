# Tun Tun Clock

Aplicativo Android de relógio desenvolvido em **Kotlin** com **Jetpack Compose** para a disciplina de Desenvolvimento de Aplicativos Móveis. O projeto reúne ferramentas para acompanhar horários e gerenciar alarmes e fusos horários, com dados salvos localmente no dispositivo.

## Funcionalidades

- **Relógio:** visualização do horário.
- **Cronômetro:** acompanhamento de tempo decorrido.
- **Timer:** contagem regressiva.
- **Alarmes:** listar, cadastrar, editar, ativar/desativar e excluir alarmes.
- **Fusos horários:** cadastrar e excluir localidades, consultar o horário atual e comparar com o horário de São Paulo.
- **Persistência local:** alarmes e fusos horários são armazenados em um banco de dados Room e continuam disponíveis depois que o aplicativo é fechado e aberto novamente.

## Tecnologias utilizadas

- Kotlin
- Android Studio
- Jetpack Compose e Material 3
- Navigation Compose
- Room
- KSP
- Gradle
- JDK 17

## Organização do projeto

Os principais arquivos da aplicação ficam em `app/src/main/java/com/example/tungclock/`:

| Arquivo | Responsabilidade |
| --- | --- |
| `MainActivity.kt` | Ponto de entrada da aplicação. |
| `AppNavigation.kt` | Organiza a navegação entre as telas e carrega os dados persistidos. |
| `RelogioScreen.kt` | Tela do relógio. |
| `CronometroScreen.kt` | Tela do cronômetro. |
| `TimerScreen.kt` | Tela do timer. |
| `AlarmesScreen.kt` | Lista, cadastro e exclusão de alarmes. |
| `DetalheAlarmeScreen.kt` | Consulta e edição de um alarme selecionado. |
| `FusosScreen.kt` | Lista, cadastro e exclusão de fusos horários. |
| `DetalheFusoScreen.kt` | Exibe o horário atual do fuso e a comparação com São Paulo. |
| `Model.kt` | Define as entidades `Alarme` e `FusoHorario` do Room. |
| `AlarmeDao.kt` | Operações de consulta e alteração dos alarmes no banco. |
| `FusoHorarioDao.kt` | Operações de consulta e alteração dos fusos no banco. |
| `AppDataBase.kt` | Configuração e instância singleton do banco de dados. |

## Persistência com Room

O banco local utilizado pela aplicação é `tungclock_database`. Ele contém as entidades `Alarme` e `FusoHorario`, com identificadores gerados automaticamente.

Os DAOs disponibilizam operações para inserir, listar, buscar por ID, atualizar e excluir registros. Ao iniciar o aplicativo, os dados são carregados do banco. Exemplos iniciais são inseridos apenas quando a tabela correspondente está vazia. As operações de acesso ao banco são executadas em `Dispatchers.IO`.

## Como executar

### Pré-requisitos

- Android Studio;
- JDK 17;
- Android SDK configurado;
- Emulador Android ou dispositivo físico.

### Passos

1. Clone o repositório:

   ```bash
   git clone https://github.com/konnopedro/Clock_Kotlin_Trabalho-3.git
   ```

2. Abra a pasta do projeto no Android Studio.
3. Aguarde a sincronização do Gradle.
4. Execute o aplicativo em um emulador ou dispositivo Android.

## Evidências do projeto

Adicione as capturas de tela ou gravações nos espaços abaixo. As evidências devem ser produzidas com o aplicativo executando e mostrar as funcionalidades descritas.

### 1. Tela principal

**O que colocar:** captura da navegação inferior mostrando as áreas Relógio, Cronômetro e Timer.

<!-- Adicionar aqui a imagem da tela principal. -->

### 2. Alarmes e edição

**O que colocar:** captura da lista de alarmes e da tela de detalhes durante a edição de um alarme. Se possível, mostre um alarme cadastrado por você.

<!-- Adicionar aqui as imagens da lista e da edição de alarmes. -->

### 3. Fusos horários

**O que colocar:** captura da lista de fusos cadastrados e da tela de detalhes mostrando o horário da localidade e a comparação com São Paulo.

<!-- Adicionar aqui as imagens da lista e dos detalhes de fusos horários. -->

### 4. Persistência após fechar e abrir o aplicativo

**O que colocar:** primeiro, uma captura mostrando um alarme e um fuso cadastrados ou um alarme editado. Depois, feche completamente o aplicativo, abra-o novamente e adicione uma segunda captura mostrando que os dados continuam salvos.

<!-- Adicionar aqui as capturas antes e depois de reabrir o aplicativo. -->

### 5. Exclusão persistente

**O que colocar:** capture a lista após excluir um alarme ou fuso e, depois de fechar e reabrir o aplicativo, mostre que o item excluído não reapareceu.

<!-- Adicionar aqui as capturas que demonstram a exclusão antes e depois de reabrir o aplicativo. -->

### 6. Vídeo de demonstração (opcional)

**O que colocar:** link de uma gravação curta demonstrando cadastro, edição, exclusão e persistência após reiniciar o aplicativo.

<!-- Adicionar aqui o link do vídeo, se houver. -->

## Disciplina

**Desenvolvimento de Aplicativos Móveis — Trabalho 3**

Projeto desenvolvido em Kotlin com Jetpack Compose.
