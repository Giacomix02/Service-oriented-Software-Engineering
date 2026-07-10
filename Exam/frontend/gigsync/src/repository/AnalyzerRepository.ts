import { Artist, Song, StreamingService } from "@/types"
import axios from "axios"
import { config } from "process"
import { Dispatch, SetStateAction } from "react"

const baseURL= "/api-gateway"

/// api/analyze/songs
export const getAllSongs = async (setSongs: Dispatch<SetStateAction<Song[]>>) => {
    const response = await axios.get(baseURL+"/api/analyze/songs", )
    const songs: Song[] = await response.data
    setSongs(songs)
}

/// api/analyze/songs/{id}
export const getSongById = async (setSongs: Dispatch<SetStateAction<Song>>) => {
    const response = await axios.get(baseURL+"/api/analyze/songs/{id}", )
    const song: Song = await response.data
    setSongs(song)
}

/// api/analyze/songs/by-name/{name}
export const getSongsByName = async (setSongs: Dispatch<SetStateAction<Song[]>>, name: string) => {
    const response = await axios.get(baseURL+"/api/analyze/songs/by-name/{name}", )
    const songs: Song[] = await response.data
    setSongs(songs)
}

/// api/analyze/songs/by-artist/{name}
export const getSongsByArtist = async (setSongs: Dispatch<SetStateAction<Song[]>>, name: string) => {
    const response = await axios.get(baseURL+"/api/analyze/songs/by-artist/{name}", )
    const songs: Song[] = await response.data
    setSongs(songs)
}

/// api/analyze/artists
export const getAllArtists = async (setArtists: Dispatch<SetStateAction<Artist[]>>) => {
    const response = await axios.get(baseURL+"/api/analyze/artists", )
    const artists: Artist[] = await response.data
    setArtists(artists)
}

/// api/analyze/artists/{id}
export const getArtistById = async (setArtists: Dispatch<SetStateAction<Artist>>) => {
    const response = await axios.get(baseURL+"/api/analyze/artists/{id}", )
    const artist: Artist = await response.data
    setArtists(artist)
}

/// api/analyze/streaming-services
export const getAllStreamingServices = async (setServices: Dispatch<SetStateAction<StreamingService[]>>) => {
    const response = await axios.get(baseURL+"/api/analyze/streaming-services", )
    const services: StreamingService[] = await response.data
    setServices(services)
}

/// events/getAllEvents
// export const getAllEvents = async (setEvents: Dispatch<SetStateAction<any[]>>) => {
//     const response = await axios.get(baseURL+"/events/getAllEvents", )
//     const events: any[] = await response.data
//     setEvents(events)
// }

/// events/getEventByID/{Event_Global_ID}
// export const getEventById = async (setEvents: Dispatch<SetStateAction<any>>) => {
//     const response = await axios.get(baseURL+"/events/getEventByID/{Event_Global_ID}", )
//     const event: any = await response.data
//     setEvents(event)
// }

/// events/searchByName/{Name}
// export const searchEventsByName = async (setEvents: Dispatch<SetStateAction<any[]>>, name: string) => {
//     const response = await axios.get(baseURL+"/events/searchByName/{Name}", )
//     const events: any[] = await response.data
//     setEvents(events)
// }

/// api/stats/artists
export const getAllStatsArtists = async (setArtists: Dispatch<SetStateAction<Artist[]>>) => {
    const response = await axios.get(baseURL+"/api/stats/artists", )
    const artists: Artist[] = await response.data
    setArtists(artists)
}

/// api/stats/artists/{id}
export const getStatsArtistById = async (setArtists: Dispatch<SetStateAction<Artist>>, id: number) => {
    const response = await axios.get(baseURL+"/api/stats/artists/{id}", )
    const artist: Artist = await response.data
    setArtists(artist)
}

/// api/stats/songs
export const getAllStatsSongs = async (setSongs: Dispatch<SetStateAction<Song[]>>) => {
    const response = await axios.get(baseURL+"/api/stats/songs", )
    const songs: Song[] = await response.data
    setSongs(songs)
}

/// api/stats/songs/{id}
export const getStatsSongById = async (setSongs: Dispatch<SetStateAction<Song>>, id: number) => {
    const response = await axios.get(baseURL+"/api/stats/songs/{id}", )
    const song: Song = await response.data
    setSongs(song)
}

/// api/stats/songs/by-name/{name}
export const getStatsSongsByName = async (setSongs: Dispatch<SetStateAction<Song[]>>, name: string) => {
    const response = await axios.get(baseURL+"/api/stats/songs/by-name/{name}", )
    const songs: Song[] = await response.data
    setSongs(songs)
}

/// api/stats/songs/by-artist/{name}
export const getStatsSongsByArtist = async (setSongs: Dispatch<SetStateAction<Song[]>>, name: string) => {
    const response = await axios.get(baseURL+"/api/stats/songs/by-artist/{name}", )
    const songs: Song[] = await response.data
    setSongs(songs)
}

/// api/stats/artists/by-name/{name}
export const getStatsArtistsByName = async (setArtists: Dispatch<SetStateAction<Artist[]>>, name: string) => {
    const response = await axios.get(baseURL+"/api/stats/artists/by-name/{name}", )
    const artists: Artist[] = await response.data
    setArtists(artists)
}
