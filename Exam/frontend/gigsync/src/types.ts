export interface Artist {
    id: number;
    name: string;
    description: string;
}



export interface Song {
    id: number;
    name: string;
    description: string;
    views: number;
    artist: Artist;
    streamingServices: StreamingService[]
}

export interface StreamingService {
    id: number;
    name: string;
    description: string
}


