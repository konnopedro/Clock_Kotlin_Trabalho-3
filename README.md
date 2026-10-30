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

## Disciplina

**Desenvolvimento de Aplicativos Móveis — Trabalho 3**

Projeto desenvolvido em Kotlin com Jetpack Compose.

---

## Trabalho 4 — Refatoração MVVM

Esta branch reorganiza o aplicativo do Trabalho 3 mantendo as mesmas entidades Room, telas e navegação. A refatoração é feita no mesmo projeto; não há servidor, login ou banco remoto.

### Antes e depois

No Trabalho 3, as telas e o `AppNavigation` faziam consultas aos DAOs e mantinham listas de estado em memória. Depois da reorganização, as telas recebem listas atualizadas pelos `StateFlow` dos ViewModels e chamam seus métodos para inserir, atualizar ou excluir registros.

### Camadas

- **`data` (Model):** `Alarme`, `FusoHorario`, seus DAOs e `AppDatabase`. As consultas `buscarTodos()` emitem `Flow<List<...>>`.
- **`data.repository`:** `AlarmeRepository` e `FusoHorarioRepository` são os pontos de acesso aos DAOs.
- **`viewmodel`:** `AlarmeViewModel` e `FusoViewModel` coletam os fluxos de registros e expõem `uiState` público e `_uiState` privado. As Factories recebem cada Repository.
- **`ui` (View):** telas Jetpack Compose de relógio, alarmes, fusos, detalhes, cronômetro e timer.
- **`AppNavigation`:** mantém o `NavHost` e fornece os ViewModels criados pelas Factories.

### Estados usados

O `AlarmeUiState` guarda a lista de alarmes, o indicador de carregamento e uma mensagem de erro opcional. O `FusoUiState` faz o mesmo para a lista de fusos. O Room notifica mudanças nas tabelas e o ViewModel atualiza o estado sem exigir uma nova consulta manual após cada operação.

### Decisões do trio e demonstração

A arquitetura mantém uma Factory por ViewModel para deixar explícita a injeção de Repository, de acordo com os exemplos do professor. A documentação sobre divisão do trabalho, dificuldades individuais e como foram resolvidas deve ser preenchida pelos integrantes com fatos reais, sem inventar experiências.

**Validação ainda necessária:** compilar no Android Studio ou GitHub Actions e demonstrar, com capturas de tela e vídeos, criação, listagem, atualização, exclusão e persistência ao fechar e reabrir o aplicativo. Anexar aqui os links de evidências e relatar qualquer problema realmente encontrado durante os testes.
