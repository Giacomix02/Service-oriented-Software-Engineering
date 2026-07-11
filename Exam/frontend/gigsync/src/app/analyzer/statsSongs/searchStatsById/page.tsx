"use client"

import styles from "../statsSongs.module.css";
import {useState} from "react";
import {Song} from "@/types";
import SearchBar from "@/components/SearchBar/SearchBar";
import {getSongById} from "@/repository/AnalyzerRepository";
import SongCard from "@/components/Cards/SongCard/SongCard";


export default function Page() {
    const [song, setSong] = useState<Song | undefined>()
    console.log(song == undefined)
    return (
        <div className={styles.pageContainer}>
            <h1 className={styles.pageTitle}>Search Song by Id Stats</h1>
            <SearchBar<Song> searchFunction={getSongById} setter={setSong} type={"number"}/>
            <div className={styles.cardsGrid}>
                <SongCard song={song}/>
            </div>
        </div>
    )
}