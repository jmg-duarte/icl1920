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
in
    let
        val:int = inc(double(pow(2, 3)))
    in
        app(inc, inc)(val)
    end
end
;;