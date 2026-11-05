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
4. DOCUMENTAÇÃO:
<a href="https://drive.google.com/file/d/1rGkMoaBIgb1VVbDxHlFkYoLno1geuAvn/view?usp=drivesdk" target="_blank">Documentação Tun Tun Clock</a>

