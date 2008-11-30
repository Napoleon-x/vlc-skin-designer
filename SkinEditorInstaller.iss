[Files]
Source: share/ICE_JNIRegistry.dll; DestDir: {app}
Source: LICENSE.TXT; DestDir: {app}
Source: README.TXT; DestDir: {app}; Flags: isreadme
Source: skin.dtd; DestDir: {app}
Source: share/VLCSkinEditor.exe; DestDir: {app}
Source: VLCSkinEditor.jar; DestDir: {app}
Source: lang/English.txt; DestDir: {app}/lang
Source: lang/english/*.txt; DestDir: {app}/lang/english/
[Icons]
Name: {group}\VLC Skin Editor; Filename: {app}\VLCSkinEditor.exe; WorkingDir: {app}; IconFilename: {app}\VLCSkinEditor.exe; IconIndex: 0
Name: {group}\Uninstall VLC Skin Editor; Filename: {uninstallexe}
Name: {group}\Online Help; Filename: http://www.videolan.org/vlc/skinedhlp/
[Setup]
OutputBaseFilename=VLCSkinEditor_setup
VersionInfoVersion=0.8
VersionInfoCompany=VideoLAN
VersionInfoDescription=Setup for the VLC Skin Editor
VersionInfoCopyright=�2008 VideoLAN
AppCopyright=�2008 VideoLAN Team
AppName=VLC Skin Editor
AppVerName=0.7.5
LicenseFile=LICENSE.TXT
PrivilegesRequired=poweruser
DefaultDirName={pf}\VideoLAN\VLC Skin Editor
DirExistsWarning=no
DefaultGroupName=VideoLAN\VLC Skin Editor
AppendDefaultGroupName=false
AppPublisher=VideoLAN
AppPublisherURL=http://www.videolan.org
AppSupportURL=http://www.videolan.org/vlc/skineditor.html
AppUpdatesURL=http://www.videolan.org/vlc/skineditor.html
AppVersion=0.7
AppID={{977C5080-EA08-435D-8901-233A506E1651}}
AppReadmeFile={app}\README.TXT
UninstallDisplayIcon={app}\VLCSkinEditor.exe
UninstallDisplayName=VLC Skin Editor
ChangesAssociations=true
OutputDir=dist
[Registry]
Root: HKCR; Subkey: .vlt; ValueType: string; ValueData: VLCSkinFile; Flags: uninsdeletevalue
Root: HKCR; Subkey: VLCSkinFile; ValueType: string; ValueData: Compressed VLC Skin; Flags: uninsdeletevalue
Root: HKCR; Subkey: VLCSkinFile\DefaultIcon; ValueType: string; ValueData: """{app}\VLCSkinEditor.exe,2"""
Root: HKCR; Subkey: VLCSkinFile\shell\open\command; ValueType: string; ValueName: ; ValueData: """{app}\VLCSkinEditor.exe"" ""%1"""
Root: HKCR; Subkey: VLCSkinFile\shell\open; ValueType: string; ValueData: Edit with VLC Skin Editor
[InstallDelete]
Name: {app}/VLCSkinEditor.cfg; Type: files
