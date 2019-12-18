let
    pow : (int, int)int = fun base: int, exp: int ->
        let
            result : ref int = new base
            mutexp : ref int = new exp
        in
            while !mutexp > 1 do
                mutexp := !mutexp - 1;
                result := !result * base
            end;
            !result
        end
    end
in
    pow(2, 3)
end
;;