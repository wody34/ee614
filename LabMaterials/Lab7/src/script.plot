set terminal png size 1024,768
set size 1,0.5
set output "result.png"
set key left top
set grid y
set xlabel 'requests'
set ylabel "response time (ms)"
set datafile separator '\t'
plot "result.plot" every ::2 using 5 title 'response time' with lines
exit