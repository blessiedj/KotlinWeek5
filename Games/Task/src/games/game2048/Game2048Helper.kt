package games.game2048

/*
 * This function moves all the non-null elements to the beginning of the list
 * (by removing nulls) and merges equal elements.
 * The parameter 'merge' specifies the way how to merge equal elements:
 * it returns a new element that should be present in the resulting list
 * instead of two merged elements.
 *
 * If the function 'merge("a")' returns "aa",
 * then the function 'moveAndMergeEqual' transforms the input in the following way:
 *   a, a, b -> aa, b
 *   a, null -> a
 *   b, null, a, a -> b, aa
 *   a, a, null, a -> aa, a
 *   a, null, a, a -> aa, a
 *
 * You can find more examples in 'TestGame2048Helper'.
*/
fun <T : Any> List<T?>.moveAndMergeEqual(merge: (T) -> T): List<T> {
   val initialList = this.filterNotNull().toList()
    val finalSequence = sequence {
        if (initialList.size == 1) {
            yield(initialList[0])
        } else if (initialList.size == 2) {
            if (initialList[0] == initialList[1]) {
                yield(merge(initialList[1]))
            } else {
                yieldAll(initialList)
            }
        } else if (initialList.size == 3) {
            if (initialList[0] == initialList[1]) {
                yield(merge(initialList[1]))
                yield(initialList[2])
            } else {
                yield(initialList[0])
                if (initialList[1] == initialList[2]) {
                    yield(merge(initialList[1]))
                }else {
                    yieldAll(listOf(initialList[1],initialList[2]))
                }
            }
        }
        else if (initialList.size == 4) {
            if (initialList[0] == initialList[1]) {
                yield(merge(initialList[1]))
                if(initialList[2] == initialList[3]){
                    yield(merge(initialList[2]))
                }
                else{
                    yieldAll(listOf(initialList[2],initialList[3]))
                }
            } else {
                yield(initialList[0])
                if (initialList[1] == initialList[2]) {
                    yield(merge(initialList[1]))
                }else {
                    yield(initialList[1])
                    if(initialList[2] == initialList[3]){
                        yield(merge(initialList[2]))
                    }
                    else{
                    yieldAll(listOf(initialList[2],initialList[3]))
                    }
                }
            }
        }
    }
return finalSequence.take(4).toList()
}
