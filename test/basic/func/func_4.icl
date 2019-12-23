let x: (int)int = fun a: int ->
    a + 5 end
in
    let y: (int)int = fun b: int ->
        b + 7 end
    in
        println(x(y(2)))
    end
end;;