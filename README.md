# Gerenciamento de Memória - 2023/2
### ** Alunos: Andressa Farkas, Angelo Corte, Felipe Tasoniero, Luana Thomas **

## Objetivos

Este projeto tem como objetivo explorar e analisar diferentes políticas de alocação de espaços em uma memório.

## Primeiros passos

- Clonar o repositorio

```
git clone https://github.com/andressafarkas/GerenciamentoMemoria.git
```

- Compilar as classes

```
$ javac ./*.java
```

- Gerar o executável

```
$ jar cfe GerenciamentoMemoria.jar App ./*.class
```

- Executar a aplicação

```
$ java -jar ./GerenciamentoMemoria.jar
```

## Como utilizar

Ao executar a aplicação, serão solicitas ao usuário as seguintes informações:

- Informar o tamanho da memória principal (potência de dois), que define o tamanho total inicial disponível para alocação;
- Informar qual política de alocação deverá ser utilizada:
  - First-Fit
  - Best-Fit
  - Worst-Fit
  - Circular-Fit

A aplicação então irá apresentar os resultados da seguinte forma:

```
Informe o tamanho da memória (apenas potências de 2): 8
Escolha a estratégia de alocação (1 - First-Fit, 2 - Worst-Fit, 3 - Best-Fit, 4 - Circular-Fit): 3
Usando Best-Fit:
IN(A, 3)         |5|
IN(B, 2)         |3|
IN(C, 1)         |2|
OUT(A)           |3|2|
IN(D, 3)         |2|
OUT(B)           |2|2|
OUT(C)           |5|
OUT(D)           |8|
```

onde a primeira coluna indica as etadas de alocação e liberação de memória indicadas no arquivo de configuração, e a segunda coluna indica o espaço livre na memória a cada passo de execução.

Caso não seja possível alocar memória em alguma etapa, a aplicação irá apresentar a seguinte informação:

```
ESPAÇO INSUFICIENTE DE MEMÓRIA
```

\*\* Caso deseje realizar seus próprios testes, siga os passos indicados na seção 'Configuração para Testes'.

## Configuração para Testes

Caso deseje realizar seu próprio teste, atualize o 'arquivo_teste.txt' de acordo com as seguintes regras de requisição de alocação e liberação de memória:

- Requisição de alocação: IN(ID, SIZE)
  - 'IN' indica o processo de alocação
  - 'ID' indica o nome do processo
  - 'SIZE' indica o tamanho/espaço do processo (valor numérico inteiro)
  - Ex.: IN(A, 10) Requisita a alocação de 10 espaços para o processo A
- Requisição de liberação: OUT(ID)
  - 'OUT' indica o processo de liberação de memória
  - 'ID' infica o nome do processo
  - Ex.: OUT(C) Requisita a liberação do espaço alocado pelo processo C

## Requerimentos

- Java JDK 11
- Windows ou Ubuntu
