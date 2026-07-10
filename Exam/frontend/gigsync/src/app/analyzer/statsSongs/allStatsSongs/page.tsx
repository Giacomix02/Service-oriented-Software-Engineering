"use client"
import {Song} from "@/types";
import { useEffect, useState } from "react";
import { getAllSongs } from "@/repository/AnalyzerRepository";
import SongCard from "@/components/Cards/SongCard/SongCard";
import styles from "../statsSongs.module.css";

export default function Page() {
    const [songs, setSongs] = useState<Song[]>([]);
    
    useEffect(() => {
        getAllSongs(setSongs)
    }, [])
    
    return (
        <div className={styles.pageContainer}>
            <h1 className={styles.pageTitle}>All Songs Stats</h1>
            <div className={styles.cardsGrid}>
                {songs.map((song) => 
                    <SongCard key={song.id} song={song} />
                )}
            </div>
        </div>
    )
}