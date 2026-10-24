(ns content.core-functions-in-depth.seq-function-examples.map)

;Missão C4-01 — Transformando coleções
;No LifeOS, você tem um vetor com os minutos dedicados a cada hábito durante o dia:
(def minutos [15 30 45 60])
;
;
;Implemente as seguintes funções:
;
;1. Converter minutos em segundos
;
;Crie converter-para-segundos, que recebe um vetor de minutos e retorna uma sequência com cada valor convertido em segundos.
;
;Exemplo de resultado: (900 1800 2700 3600)
;
;2. Aumentar a duração dos hábitos
;
;Crie aumentar-duracao, que recebe um vetor de minutos e adiciona 10 minutos a cada elemento.
;
;Exemplo de resultado: (25 40 55 70)
;
;3. Formatar os hábitos
;
;Crie formatar-habitos, que recebe um vetor de nomes de hábitos e retorna uma sequência com cada nome convertido para letras maiúsculas.
;
;Exemplo de entrada:
;["leitura" "exercício" "estudos"]
;
;
;Exemplo de resultado: ("LEITURA" "EXERCÍCIO" "ESTUDOS")