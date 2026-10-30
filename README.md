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

## Organização do projeto (Trabalho 4 — MVVM)

O aplicativo continua com as sete telas anteriores. A refatoração separa o acesso ao banco da interface, sem adicionar serviços externos.

| Pacote | Responsabilidade |
| --- | --- |
| `data` | `Alarme`, `FusoHorario`, DAOs e `AppDatabase`, mantendo as tabelas Room existentes. |
| `data.repository` | `AlarmeRepository` e `FusoHorarioRepository`: únicas classes de domínio que acessam os DAOs. |
| `viewmodel` | `AlarmeViewModel`, `FusoViewModel`, os respectivos `UiState` e as `ViewModelFactory`. |
| `ui` | Telas Compose, sem operações diretas no banco. |
| raiz | `MainActivity` e `AppNavigation`, onde a navegação e a criação dos ViewModels são ligadas às telas. |

### Decisões de arquitetura

**Antes (Trabalho 3):** os Composables consultavam o DAO diretamente, acionavam corrotinas para gravar registros e atualizavam as listas manualmente.

**Depois (Trabalho 4):** a tela chama ações do ViewModel; ele acessa o Repository, que chama o DAO. A consulta `buscarTodos()` dos dois DAOs retorna `Flow<List<...>>`; o ViewModel coleta esse fluxo e expõe `MutableStateFlow` privado com `StateFlow` público. As telas observam o estado e se redesenham quando a lista muda.

Os `AlarmeUiState` e `FusoUiState` agrupam a lista, o indicador de carregamento e a mensagem de erro. As `ViewModelFactory` recebem os Repositories e os fornecem aos ViewModels, sem usar Hilt. O Room continua local, com as mesmas entidades e nome do banco.

### Evidências e apresentação (a completar pelo trio)

Incluam capturas ou links de vídeos feitos **no aplicativo em execução**, demonstrando:
- criação, edição, ativação/desativação e exclusão de um alarme;
- inclusão e exclusão de cidades e navegação para os detalhes;
- fechamento completo e reabertura do aplicativo, confirmando os dados persistidos;
- exibição da estrutura `ui / data / data.repository / viewmodel` no Android Studio.

**Dificuldades e participação do trio:** registrar aqui, com as próprias palavras, os problemas reais encontrados e como cada integrante ajudou a resolvê-los. Não preencher com ocorrências inventadas.

## Persistência com Room

O banco local utilizado pela aplicação é `tungclock_database`. Ele contém as entidades `Alarme` e `FusoHorario`, com identificadores gerados automaticamente.

Os DAOs disponibilizam operações para inserir, listar, buscar por ID, atualizar e excluir registros. As consultas de listas usam `Flow`, e as gravações são realizadas pelos ViewModels com `viewModelScope`. O banco permanece no dispositivo.

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
