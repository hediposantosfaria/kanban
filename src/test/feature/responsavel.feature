# language: pt

Funcionalidade: Gestão de Responsáveis

  Cenário: Consultar todos os responsáveis e retornar vazio
    Quando é realiza uma requisição para consultar todos os responsáveis
    Então o sistema deve retornar os dados de responsáveis vazios

  Cenário: Consultar todos os responsáveis
    Dado que existe o seguinte responsável cadastrado
      | Nome  | Email             |
      | Bento | bento@empresa.com |
    E que existe o seguinte responsável cadastrado
      | Nome   | Email              |
      | Mariah | mariah@empresa.com |
    Quando é realiza uma requisição para consultar todos os responsáveis
    Então o sistema deve retornar os seguintes dados de responsáveis
      | Nome   | Email              |
      | Bento  | bento@empresa.com  |
      | Mariah | mariah@empresa.com |

  Cenário: Buscar responsável por id com sucesso
    Dado que existe o seguinte responsável cadastrado
      | Nome  | Email             |
      | Bento | bento@empresa.com |
    Quando é realizada uma requisição para consultar um responsável
    Então o sistema deve retornar o seguinte dado de responsável
      | Nome  | Email             |
      | Bento | bento@empresa.com |

  Cenário: Buscar responsável inexistente por id
    Quando é realizada uma requisição para consultar um responsável
    Então a resposta deve ser responsável não encontrado

  Cenário: Atualizar responsável com sucesso
    Dado que existe o seguinte responsável cadastrado
      | Nome | Email           |
      | Ana  | ana@empresa.com |
    Quando é realizada uma requisição para altererar um responsável
      | Nome      | Email                 |
      | Ana Paula | ana.paula@empresa.com |
    Então o sistema deve retornar o seguinte dado de responsável
      | Nome      | Email                 |
      | Ana Paula | ana.paula@empresa.com |

  Cenário: Atualizar responsável inexistente
    Quando é realizada uma requisição para altererar um responsável
      | Nome      | Email                 |
      | Ana Paula | ana.paula@empresa.com |
    Então a resposta deve ser responsável não encontrado

  Cenário: Deletar responsável com sucesso
    Dado que existe o seguinte responsável cadastrado
      | Nome  | Email             |
      | Bento | bento@empresa.com |
    Quando é realizada uma requisição para apagar um responsável
    Então o sistema deve apagar o responsável com sucesso

  Cenário: Deletar responsável inexistente
    Quando é realizada uma requisição para apagar um responsável
    Então a resposta deve ser responsável não encontrado

  Cenário: Inserir responsável com sucesso
    Quando é realizada uma requisição para inserir um responsável
      | Nome  | Email             |
      | Bento | bento@empresa.com |
    Então o sistema deve retornar o seguinte dado de responsável
      | Nome  | Email             |
      | Bento | bento@empresa.com |
