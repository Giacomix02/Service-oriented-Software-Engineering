"use client"
import {Song} from "@/types";
import SongCard from "@/components/SongCard/SongCard";
import { useEffect, useState } from "react";
import { getAllSongs } from "@/repository/AnalyzerRepository";

export default function Page() {
    const [songs, setSongs] = useState<Song[]>([]);
    useEffect(() => {
        getAllSongs(setSongs)
    }, [])
    
    return (
        <div>
            <h1>Analyzer</h1>
            
            {songs.map((song) => 
        <SongCard key={song.id} song={song} />
    )}
        </div>
       
    )
}