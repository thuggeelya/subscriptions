package ru.thuggeelya.subscriptions.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import ru.thuggeelya.subscriptions.dto.system.UserDto;
import ru.thuggeelya.subscriptions.dto.request.UserCreateDto;
import ru.thuggeelya.subscriptions.dto.request.UserUpdateDto;
import ru.thuggeelya.subscriptions.entity.User;

import static java.time.LocalDateTime.now;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        builder = @Builder(disableBuilder = true)
)
public interface UserMapper {

    UserDto toUserDto(final User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    User toUser(final UserCreateDto dto);

    @AfterMapping
    default void finishMapping(@MappingTarget final User user, final UserCreateDto dto) {

        if (dto instanceof UserUpdateDto updateDto) {

            user.setId(updateDto.getId());
            user.setCreated(updateDto.getCreated());
        } else {
            user.setCreated(now());
        }
    }
}
