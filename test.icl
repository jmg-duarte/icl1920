let x : int = 1
    in
        let y : int = 2 + x
            f : (int)int = fun z : int -> z + x end
    in
        x+f(x)
    end + x
end
;;