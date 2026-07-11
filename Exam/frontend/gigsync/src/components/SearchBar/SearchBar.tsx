"use client"
import { Dispatch, SetStateAction, useEffect, useState } from "react"
import styles from "./SearchBar.module.css"

type inputProps = "text" | "number"

export default function SearchBar<T>(props: { searchFunction: (param:string, setter: Dispatch<SetStateAction<T | undefined>>) => Promise<void>, setter: Dispatch<SetStateAction<T | undefined>>, type : inputProps }) {
    const inputType = props.type
    const func = props.searchFunction
    const setter = props.setter
    const [input, setInput] = useState("")

    useEffect(() => {
        if(input.valueOf() === "" || input.valueOf() === ";" || input.valueOf() === " " || input.valueOf() === ".") setter(undefined)
        else if(input.length > 0) func(input.valueOf(), setter)
        else setter(undefined)
    })
    
    return (
     
        <div className={styles.searchBar}>
            <input type={inputType} className={styles.textField} onChange={event => setInput(event.target.value)}>

            </input>
            <div className={styles.separator}></div>
            <button className={styles.button} onClick={() => func(input.valueOf(), setter)}>🔎</button>

        </div>


    )
}