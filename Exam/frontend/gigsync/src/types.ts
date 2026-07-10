export interface Artist {
    id: number;
    name: string;
    description: string;
}

export interface Song {
    id: number;
    name: string;
    description: string;
    plays: number;
    artist: Artist;
    streamingServices: StreamingService[]
}

export interface StreamingService {
    name: string;
    description: string
}