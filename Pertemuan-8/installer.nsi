; Script NSIS untuk membuat installer PenilaianMhs

Name "Aplikasi Penilaian Mahasiswa"
OutFile "Penilaian-Installer.exe"
InstallDir "C:\Penilaian"

; Halaman installer
Page directory
Page instfiles

Section "Install"
    SetOutPath "$INSTDIR"

    ; Salin semua file dari ZIP ke folder instalasi
    File "PenilaianMhs.exe"
    File /r "lib\"
    File /r "src\"

    ; Buat shortcut di Desktop
    CreateShortCut "$DESKTOP\PenilaianMhs.lnk" "$INSTDIR\PenilaianMhs.exe"

    ; Buat shortcut di Start Menu
    CreateDirectory "$SMPROGRAMS\PenilaianMhs"
    CreateShortCut "$SMPROGRAMS\PenilaianMhs\PenilaianMhs.lnk" "$INSTDIR\PenilaianMhs.exe"

SectionEnd

Section "Uninstall"
    Delete "$INSTDIR\PenilaianMhs.exe"
    RMDir /r "$INSTDIR\lib"
    RMDir /r "$INSTDIR\src"
    Delete "$DESKTOP\PenilaianMhs.lnk"
    RMDir /r "$SMPROGRAMS\PenilaianMhs"
    RMDir "$INSTDIR"
SectionEnd
