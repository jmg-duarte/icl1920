println let
    x : int = 1 
in 
    let y : int = 2 + x
        f : (int)int = fun z : int -> 
            z + x 
        end
    in
        x + f(y)
    end + x
end
;;