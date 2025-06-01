package com.example.selaluada.data.model

data class CustomerRequestDTO(
    val nik: String,
    val tempat_lahir: String,
    val tanggal_lahir: String,  // format yyyy-MM-dd
    val gender: String,
    val pekerjaan: String,
    val gaji: Long,
    val bank: String,
    val rekening: Long,
    val no_hp: String,
    val nama_ibu_kandung: String,
    val alamat: String,
    val provinsi: String
)
