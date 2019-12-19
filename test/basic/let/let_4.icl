println let 
    x : ref int = new 5
    y : ref int = new 10
in 
    !x * (!y + 1) - 5
end
;;