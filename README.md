# pokemon-view
Aplicativo Android que simula uma Pokédex, uma enciclopédia virtual contendo todas as espécies de Pokémon, consultando informações por meio de requisições à https://pokeapi.co/.

A ideia principal é poder navegar em uma lista que contem todos os pokemons e poder ver descrição mais detalhada de cada um deles. Até o momento, como implementação adicional, foi implementada a função de pesquisar por número do pokemon.

## Estrutura da aplicação
A aplicação foi feita baseada na arquitetura MVVM. Seguindo essa arquitetura os diretórios de mais alto nível da aplicação estão divididos assim:

![image](https://github.com/romanhole/pokemon-view/assets/49032796/77d430d0-c9b3-4abf-bf25-4d7304ed8296)


### Camada de Dados
A camada de dados se estrutura da seguinte forma:

![image](https://github.com/romanhole/pokemon-view/assets/49032796/661b0795-7669-4311-b968-83527c75f821)


- *di* cuida da injeção de dependencias utilizando o Dagger Hilt
- *network* cuida da conexão com a API utilizando Retrofit e, contém os modelos de objetos mapeados a partir dos retornos da API
- *repository* contém os repositórios da aplicação, os quais fornecem os dados para os viewModels, no momento, provenientes da API

### Camada de UI
A camada de ui se estrutura da seguinte forma:

![image](https://github.com/romanhole/pokemon-view/assets/49032796/2057519d-36d6-485e-8f6a-de2457259d58)

- *components* contém os componentes composables usados em várias screens ou utilizados em listas
- *data* contém as classes de suporte para a passagem dos dados:
  - ResponseData é o objeto retornado em chamadas de repositório e viewmodel.
  - ErrorResponse é um enum com a funcionalidade de mapear os possíveis erros da aplicação.
  - UiState é o modelo de classe que controla o estado da tela
- *navigation* contém as classes necessárias para controlar a navegação do app, utilizando NavController
- *screens* contém todas as telas do app
- *theme* controla o tema do aplicativo. Dimen contém dimensões padronizadas
- *viewmodel* contém todos os viewmodels da aplicação

## A fazer
- Trocar nome dos tipos dos pokemons pelas imagens que representam os tipos, por exemplo, o tipo fogo:
  ![image](https://github.com/romanhole/pokemon-view/assets/49032796/d37fe713-5c87-48f4-9049-5d8d6ee197c2)


- Alterar as font families dos textos ao redor do aplicativo para dar uma aparência mais única

- Adicionar função de buscar por geração

- Melhorar o tratamento de erros, mapeando mais erros, mensagens mais amigáveis e permitindo ao usuário caminhos para possíveis resoluções

- Adicionar cache sob demanda utilizando o Room

- Adicionar suporte para português

- Adicionar splash screen

- Adicionar loading state
