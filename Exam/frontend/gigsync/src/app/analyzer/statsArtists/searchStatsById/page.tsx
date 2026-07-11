"use client"

import styles from "../statsArtists.module.css";
import {useState} from "react";
import {Artist, Song} from "@/types";
import SearchBar from "@/components/SearchBar/SearchBar";
import {getArtistById, getSongById} from "@/repository/AnalyzerRepository";
import SongCard from "@/components/Cards/SongCard/SongCard";
import ArtistCard from "@/components/Cards/ArtistCard/ArtistCard";



export default function Page() {
    const [artist, setArtist] = useState<Artist | undefined>()
    console.log(artist == undefined)
    return (
        <div className={styles.pageContainer}>
            <h1 className={styles.pageTitle}>Search Artist by Id</h1>
            <SearchBar<Artist> searchFunction={getArtistById} setter={setArtist} type={"number"}/>
            <div className={styles.cardsGrid}>
                <ArtistCard artist={artist}/>
            </div>
        </div>
    )
}