# Arquitetura

![Diagrama da arquietura](https://github.com/jhonatan98rios/naive-bayes-classifier-infra/blob/main/diagram.png?raw=true)

## [API (Spring)](https://github.com/jhonatan98rios/naive-bayes-classifier-api-spring)
#### Publisher
- Faz o upload do arquivo de treino
- Recebe a requisição com o arquivo de treino e dados do classifier
- Valida o arquivo
- Cria um documento no mongodb com os dados do classificador
- Publica evento no SQS

#### API
- Lista todos os classificadores criados
- Exibe um classificador por ID
- Exibe o status de um classificador por ID