"use client"

import styles from "../statsSongs.module.css";
import {useState} from "react";
import {Song} from "@/types";
import SearchBar from "@/components/SearchBar/SearchBar";
import {getSongsByArtist} from "@/repository/AnalyzerRepository";
import SongCard from "@/components/Cards/SongCard/SongCard";

export default function Page() {
    const [songs, setSongs] = useState<Song[] | undefined>()
    console.log(songs == undefined)
    return (
        <div className={styles.pageContainer}>
            <h1 className={styles.pageTitle}>Search Songs by Artist Stats</h1>
            <SearchBar<Song[]> searchFunction={getSongsByArtist} setter={setSongs} type={"text"}/>
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