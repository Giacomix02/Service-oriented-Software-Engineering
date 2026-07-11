"use client"
import SearchBar from "@/components/SearchBar/SearchBar";
import styles from "../songs.module.css";
import { useState } from "react";
import { Song } from "@/types";
import {getStatsSongById} from "@/repository/AnalyzerRepository";
import SongCard from "@/components/Cards/SongCard/SongCard";


export default function Page() {
    const [song, setSong] = useState<Song | undefined>()
    console.log(song == undefined)
    return (
        <div className={styles.pageContainer}>
            <h1 className={styles.pageTitle}>Search Song by Id</h1>
            <SearchBar<Song> searchFunction={getStatsSongById} setter={setSong} type={"number"}/>
            <div className={styles.cardsGrid}>
                <SongCard song={song}/>
            </div>
        </div>
    )
}