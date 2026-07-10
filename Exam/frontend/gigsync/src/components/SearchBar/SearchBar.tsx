"use client"
import { Dispatch, SetStateAction, useEffect, useState } from "react"
import styles from "./SearchBar.module.css"

export default function SearchBar<T>(props: { searchFunction: (param:string, setter: Dispatch<SetStateAction<T | undefined>>) => Promise<void>, setter: Dispatch<SetStateAction<T | undefined>> }) {
    const func = props.searchFunction
    const setter = props.setter
    const [input, setInput] = useState("")

    useEffect(() => {
        func(input.valueOf(), setter)
    })
    
    return (
     
        <div className={styles.searchBar}>
            <input className={styles.textField} onChange={event => setInput(event.target.value)}>

            </input>
            <div className={styles.separator}></div>
            <button className={styles.button} onClick={() => func(input.valueOf(), setter)}>🔎</button>

        </div>


    )
}