package bg.tu_varna.sit.library.utils.converters.reader_profile;

import bg.tu_varna.sit.library.data.entities.ReaderProfile;
import bg.tu_varna.sit.library.models.reader_profile_table_view.ReaderProfileData;
import bg.tu_varna.sit.library.utils.annotations.Mapper;
import bg.tu_varna.sit.library.utils.converters.base.Converter;

@Mapper(from = ReaderProfile.class, to = ReaderProfileData.class)
public class FromReaderProfileToReaderProfileData implements Converter<ReaderProfile, ReaderProfileData> {
    @Override
    public ReaderProfileData convert(ReaderProfile source) {
        return ReaderProfileData.builder()
                .createdAt(source.getCreatedAt() != null ? source.getCreatedAt().toLocalDate() : null)
                .isApproved(source.getIsProfileApproved())
                .updatedAt(source.getUpdatedAt() != null ? source.getUpdatedAt().toLocalDate() : null)
                .username(source.getUser().getUserCredentials().getUsername())
                .build();
    }
}
