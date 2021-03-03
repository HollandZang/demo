package com.holland.aviator

import com.googlecode.aviator.AviatorEvaluator
import com.googlecode.aviator.Expression


class Main {
}

fun main(args: Array<String>) {
    val script: Expression = AviatorEvaluator.getInstance().compile("""println("Hello, AviatorScript!")""")
    script.execute()

    val expression = "a-(b-c) > 100"
    val compiledExp = AviatorEvaluator.compile(expression)
    // Execute with injected variables.
    // Execute with injected variables.
    val result = compiledExp.execute(compiledExp.newEnv("a", 100.3, "b", 45, "c", -199.100)) as Boolean
    println(result)

}