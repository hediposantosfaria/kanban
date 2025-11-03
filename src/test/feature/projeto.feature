# language: pt

Funcionalidade: Gestão de Projetos

  Cenário: Consultar todos os projetos e retornar vazio
    Quando é realizada uma requisição para consultar todos os projetos
    Então o sistema deve retornar os dados de projetos vazios

  Cenário: Consultar todos os projetos
    Dado que existe o seguinte projeto cadastrado
      | Nome |
      | Nova |
    E que existe o seguinte projeto cadastrado
      | Nome |
      | XPTO |
    Quando é realizada uma requisição para consultar todos os projetos
    Então o sistema deve retornar os seguintes dados de projetos
      | Nome |
      | Nova |
      | XPTO |

  Cenário: Buscar projeto por id com sucesso
    Dado que existe o seguinte projeto cadastrado
      | Nome |
      | Nova |
    Quando é realizada uma requisição para consultar um projeto
    Então o sistema deve retornar o seguinte dado de projeto
      | Nome |
      | Nova |

  Cenário: Buscar projeto inexistente por id
    Quando é realizada uma requisição para consultar um projeto
    Então a resposta deve ser projeto não encontrado

  Cenário: Atualizar projeto com sucesso
    Dado que existe o seguinte projeto cadastrado
      | Nome |
      | Nova |
    Quando é realizada uma requisição para altererar um projeto
      | Nome |
      | XPTO |
    Então o sistema deve retornar o seguinte dado de projeto
      | Nome |
      | XPTO |

  Cenário: Atualizar projeto inexistente
    Quando é realizada uma requisição para altererar um projeto
      | Nome |
      | XPTO |
    Então a resposta deve ser projeto não encontrado

  Cenário: Deletar projeto com sucesso
    Dado que existe o seguinte projeto cadastrado
      | Nome |
      | Nova |
    Quando é realizada uma requisição para apagar um projeto
    Então o sistema deve apagar o projeto com sucesso

  Cenário: Deletar projeto inexistente
    Quando é realizada uma requisição para apagar um projeto
    Então a resposta deve ser projeto não encontrado

  Cenário: Inserir projeto com sucesso
    Quando é realizada uma requisição para inserir um projeto
      | Nome |
      | Nova |
    Então o sistema deve retornar o seguinte dado de projeto
      | Nome |
      | Nova |
