(ns content.others.loop-recur)


(loop [contador 1]
  (if (<= contador 5)
    (do
      (println contador)
      (recur (inc contador)))
    "Fim"))

;loop [contador 1]
;Cria a variável local contador, iniciando com o valor 1.

;if (<= contador 5)
;Verifica se o contador ainda é menor ou igual a 5.

;recur (inc contador)
;Volta ao início do loop, atualizando o contador com o próximo valor. É assim que a repetição acontece.


;Implemente uma função chamada contar-ate que receba um número limite e imprima os números de 1 até esse limite. No final, deve retornar a string "Fim".
(defn contar-ate
  [limite]
  (loop [contador 1]
    (if (<= contador limite)
      (do
        (println contador)
        (recur (inc contador)))
      "Fim")))

(contar-ate 3)



;Agora vamos aplicar o mesmo conceito a um problema mais próximo do LifeOS.
;Imagine que você tem estes aportes:
(def aportes [100 250 300 150])

;Queremos criar uma função somar-aportes que calcule o total usando loop e recur, sem usar reduce.

(defn somar-aportes
  [aportes]
  (loop [contador 0
         valor-somado 0]
    (if (< contador (count aportes))
      (recur (inc contador) (+ valor-somado (nth aportes contador)))
      valor-somado)))


;O resultado esperado é:
(somar-aportes [100 250 300 150])
;;; => 800

(defn valor-somar
  [acumulado aporte]
  (if (pos? aporte)
    (+ acumulado aporte)
    acumulado))

(defn somar-aportes-positivos
  [aportes]
  (loop [contador 0
         valor-somado 0]
    (if (< contador (count aportes))
      (recur (inc contador) (valor-somar valor-somado (nth aportes contador)))
      valor-somado)))
(somar-aportes-positivos [100 250 -50 0 300])

(defn encontrar-aporte-alto
  [aportes]
  (loop [contador 0]
    (if (>= contador (count aportes))
      nil
      (if (>= (nth aportes contador) 1000)
        (nth aportes contador)
        (recur (inc contador))))))

(encontrar-aporte-alto [100 250 800 1500 3000])
; => 1500
(encontrar-aporte-alto [100 250 800])
; => nil



(defn add-aporte-positvo
  [mapa aporte]
  (if (pos? aporte)
    (conj mapa aporte)
    mapa))

;
;(defn selecionar-aportes-positivos
;  [aportes]
;  (loop [contador 0
;         mapa-positivos []]
;    (if (< contador (count aportes))
;      (recur (inc contador) (add-aporte-positvo mapa-positivos (nth aportes contador) ))
;      mapa-positivos)))

(defn selecionar-aportes-positivos
  [aportes]
  (loop [contador 0
         mapa-positivos []]
    (if (< contador (count aportes))
      (recur (inc contador) ((fn [mapa aporte] (if (pos? aporte) (conj mapa aporte) mapa)) mapa-positivos (nth aportes contador)  ))
      mapa-positivos)))

;Resultado esperado:
(selecionar-aportes-positivos [100 -50 250 0 300 -20])
;;; => [100 250 300]

(selecionar-aportes-positivos [])
;;; => []
;
(selecionar-aportes-positivos [-10 0 -5])
;;; => []


;Vamos aumentar um pouco a dificuldade, agora combinando duas condições.

(def despesas [120 80 450 30 700 150])

(defn somar-despesas-validas
  [valor-total-despesas valor-despesa]
  (if (and (> valor-despesa 100) (<= valor-despesa 500))
    (+ valor-total-despesas valor-despesa)
    valor-total-despesas))

;Crie a função somar-despesas-altas que some apenas as despesas maiores que 100 e menores ou iguais a 500.
(defn somar-despesas-altas
  [despesas]
  (loop [contador 0
         soma 0]
    (if (< contador (count despesas))
      (recur (inc contador) (somar-despesas-validas soma (nth despesas contador)))
      soma)))

(somar-despesas-altas [120 80 450 30 700 150])
; => 720


