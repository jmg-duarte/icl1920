let
    inc: (int)int = fun n: int -> n + 1 end
    double : (int)int = fun n: int -> n * 2 end
    pow : (int, int)int = fun base: int, exp: int ->
        let
            mutexp : ref int = new exp
            result : ref int = new base
        in
            while !mutexp > 1 do
                result := !result * base;
                mutexp := !mutexp - 1
            end;
            !result
        end
    end
    app: ((int)int, (int)int)(int)int = fun f: (int)int, g: (int)int ->
        fun n: int ->
            f(g(n))
        end
    end
    fact: (int)int = fun n: int ->
        let
            result: ref int = new 1
            counter: ref int = new n
        in
            while !counter > 0 do
                result := !result * !counter;
                counter := !counter - 1
            end;
            !result
        end
    end
in
    let
        val:int = inc(double(pow(2, 3)))
    in
        println app(inc, inc)(val) ;
        println fact(10)
    end
end
;;