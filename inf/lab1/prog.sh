#!/usr/bin/env bash

read x
if ! [[ $x =~ ^-?[0-9]+$ ]] ; then
    echo "not a number" >&2; exit 1
fi

if [[ $x == 0 ]]; then 
    echo "0"
    exit 0
fi

function abs() {
    if (( $1 < 0 )); then
        echo $(((0-$1)))
        return
    fi
    echo $1
}

# Определим i - количество разрядов в числе x
gp=(0 3)
i=1
while (( $(abs $x) > gp[$i] )); do
    i1=$(((i+1)))
    gp[$i1]=$(((gp[$i] * 7 + 3)))
    i=$i1
done

((i--))

# x n
function rec {
    x=$1
    n=$2

    if [[ "$x" == 0 ]]; then
        return
    fi

    if ! [[ "$n" =~ ^[0-9]+$ ]]; then
        return
    fi

    g=${gp[$n]}

    if (( x < 0 )); then
        sign=-1
    else
        sign=1
    fi

    ((x *= sign))

    for c in {0..3}; do
        ((p = c * 7**n))
        ((d = x - p))

        if (( $(abs $d) <= g )); then
            if (( sign < 0 )) && (( c != 0 )); then  
                printf "{^%d}" $c
            else 
                printf "%d" $c
            fi

            if (( d == 0 )) && (( n != 0)); then 
                printf "%0.s0" $(seq 1 $n)
            else
                ((d = d * sign))
                ((n2=n-1))
                rec $d $n2
                break
            fi
        fi
    done
}

rec $x $i

echo 
