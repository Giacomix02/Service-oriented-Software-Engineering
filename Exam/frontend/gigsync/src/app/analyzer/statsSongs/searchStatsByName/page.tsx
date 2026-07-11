"use client"

import styles from "../statsSongs.module.css";
import {useState} from "react";
import {Song} from "@/types";
import SearchBar from "@/components/SearchBar/SearchBar";
import {getSongsByName} from "@/repository/AnalyzerRepository";
import SongCard from "@/components/Cards/SongCard/SongCard";


export default function Page() {
    const [songs, setSongs] = useState<Song[] | undefined>()
    console.log(songs == undefined)
    return (
        <div className={styles.pageContainer}>
            <h1 className={styles.pageTitle}>Search Songs by Name Stats</h1>
            <SearchBar<Song[]> searchFunction={getSongsByName} setter={setSongs} type={"text"}/>
            <div className={styles.cardsGrid}>
                {
                    songs?.map((song) => (
                        <SongCard key={song.id} song={song}/>
                    ))
                }
            </div>
        </div>
    )
}